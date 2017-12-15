/*
 * 版权所有 (C) 2015 知启蒙(ZHIQIM) 保留所有权利。

 * 
 * 指定登记&发行网站： https://www.zhiqim.com/ 欢迎加盟知启蒙，[编程有你，知启蒙一路随行]。
 *
 * 本文采用《知启蒙许可证》，除非符合许可证，否则不可使用该文件！
 * 1、您可以免费使用、修改、合并、出版发行和分发，再授权软件、软件副本及衍生软件；
 * 2、您用于商业用途时，必须在原作者指定的登记网站，按原作者要求进行登记；
 * 3、您在使用、修改、合并、出版发行和分发时，必须包含版权声明、许可声明，及保留原作者的著作权、商标和专利等知识产权；
 * 4、您在互联网、移动互联网等大众网络下发行和分发再授权软件、软件副本及衍生软件时，必须在原作者指定的发行网站进行发行和分发；
 * 5、您可以在以下链接获取一个完整的许可证副本。
 * 
 * 许可证链接：http://zhiqim.org/licenses/zhiqim_register_publish_license.htm
 * 
 * 除非法律需要或书面同意，软件由原始码方式提供，无任何明示或暗示的保证和条件。详见完整许可证的权限和限制。
 */
package org.zhiqim.gitsolo;

import java.io.File;
import org.zhiqim.git.GitServer;
import org.zhiqim.gitsolo.dbo.ZpmRepository;
import org.zhiqim.httpd.HttpRequest;
import org.zhiqim.httpd.context.annotation.AnIntercept;
import org.zhiqim.kernel.Global;
import org.zhiqim.kernel.annotation.AnAlias;
import org.zhiqim.kernel.util.Files;
import org.zhiqim.kernel.util.Validates;
import org.zhiqim.manager.ZmrSessionUser;
import org.zhiqim.orm.ZTable;
import org.zhiqim.orm.dbo.Selector;
import org.zhiqim.orm.dbo.Updater;
import org.zhiqim.project.ZpmProjectDao;
import org.zhiqim.project.dbo.ZpmProject;

/**
 * Gitsolo展示器
 *
 * @version v1.0.0 @author zouzhigang 2017-12-14 新建与整理
 */
@AnAlias("GitsoloPresenter")
@AnIntercept("chkZmrLogin")
public class GitsoloPresenter
{
    /**
     * 重命名仓库编码，先修改文件夹名称，成功后再修改数据库
     * 
     * @param request           请求
     * @param repositoryId      仓库编号
     * @param newRepositoryCode 新仓库编码
     * @throws Exception        异常
     */
    public static void rename(HttpRequest request, long repositoryId, String newRepositoryCode) throws Exception
    {
        if (!Validates.isFileName(newRepositoryCode))
        {
            request.setResponseError("新仓库编码不合法，请重选一个编码");
            return;
        }
        
        ZpmRepository item = Global.get(ZTable.class).item(ZpmRepository.class, repositoryId);
        if (item == null)
        {
            request.setResponseError("该仓库不存在");
            return;
        }
        
        if (!ZpmProjectDao.chkProjectId(request, item.getProjectId()))
        {//判断是否切换了默认项目
            return;
        }
        
        if (newRepositoryCode.equals(item.getRepositoryCode()))
        {//相同不处理
            request.setResponseError("新仓库编码未修改");
            return;
        }
        
        //判断是否存在相同的
        Selector selector = new Selector();
        selector.addMust("projectId", item.getProjectId());
        selector.addMust("repositoryCode", newRepositoryCode);
        selector.addMustNotEqual("repositoryId", repositoryId);
        if (Global.get(ZTable.class).count(ZpmRepository.class, selector) > 0)
        {
            request.setResponseError("新仓库编码["+newRepositoryCode+"]在该工程下已存在，请重选一个编码");
            return;
        }
        
        String repositoryPath = Gitsolo.getRepositoryPath(item.getProjectId(), item.getRepositoryCode());
        
        GitServer server = Global.getService(GitServer.class);
        String gitRootPath = server.getRootPath(repositoryPath);
        
        //1.删除Git缓存
        server.remove(gitRootPath);
        
        //2.重命名文件夹
        String newRepositoryPath = Gitsolo.getRepositoryPath(item.getProjectId(), newRepositoryCode);
        String newGitRootPath = server.getRootPath(newRepositoryPath);
        File file = new File(gitRootPath);
        if (!file.renameTo(new File(newGitRootPath)))
        {
            request.setResponseError("新仓库编码["+newRepositoryCode+"]重命名失败，请重试");
            return;
        }
        
        //3.修改到数据库
        Updater updater = new Updater();
        updater.addMust("repositoryId", repositoryId);
        updater.addField("repositoryCode", newRepositoryCode);
        Global.get(ZTable.class).update(ZpmRepository.class, updater);
    }
    
    /**
     * 迁移仓库到新的项目下
     * 
     * @param request       请求
     * @param repositoryId  仓库编号
     * @param destProjectId 新的项目编号
     * @throws Exception    异常
     */
    public static void move(HttpRequest request, long repositoryId, long destProjectId) throws Exception
    {
        ZpmRepository item = Global.get(ZTable.class).item(ZpmRepository.class, repositoryId);
        if (item == null)
        {
            request.setResponseError("该仓库不存在");
            return;
        }
        
        if (!ZpmProjectDao.chkProjectId(request, item.getProjectId()))
        {//判断是否切换了默认项目
            return;
        }
        
        if (Global.get(ZTable.class).count(ZpmProject.class, destProjectId) == 0)
        {
            request.setResponseError("请选择一个有效的目标项目");
            return;
        }
        
        String repositoryCode = item.getRepositoryCode();
        if (Global.get(ZTable.class).count(ZpmRepository.class, new Selector("projectId", destProjectId).addMust("repositoryCode", item.getRepositoryCode())) > 0)
        {
            request.setResponseError("目标项目已有["+repositoryCode+"]仓库编码，请先重命名后再迁移");
            return;
        }
        
        //检查工程文件夹是否存在
        GitServer server = Global.getService(GitServer.class);
        String newProjectDir = server.getRootPath("/" + destProjectId);
        Files.mkDirectory(newProjectDir);
        
        String repositoryPath = Gitsolo.getRepositoryPath(item.getProjectId(), item.getRepositoryCode());
        String gitRootPath = server.getRootPath(repositoryPath);
        
        //1.删除Git缓存
        server.remove(gitRootPath);
        
        //2.迁移文件夹到新的工程下
        String newRepositoryPath = Gitsolo.getRepositoryPath(destProjectId, item.getRepositoryCode());
        String newGitRootPath = server.getRootPath(newRepositoryPath);
        File file = new File(gitRootPath);
        if (!file.renameTo(new File(newGitRootPath)))
        {
            request.setResponseError("迁移到新项目["+destProjectId+"]失败，请重试");
            return;
        }
        
        //3.修改到数据库
        Updater updater = new Updater();
        updater.addMust("repositoryId", repositoryId);
        updater.addField("projectId", destProjectId);
        Global.get(ZTable.class).update(ZpmRepository.class, updater);
    }
    
        /**
         * 设置独立密钥
         * 
         * @param request       请求对象
         * @param projectId     项目编号
         * @throws Exception    异常
         */
        public static void setGitsoloSecret(HttpRequest request, String gitsoloSecret) throws Exception
        {
            ZmrSessionUser sessionUser = request.getSessionUser(ZmrSessionUser.class);
            GitsoloDao.setGitsoloSecret(sessionUser, gitsoloSecret);
        }
}
