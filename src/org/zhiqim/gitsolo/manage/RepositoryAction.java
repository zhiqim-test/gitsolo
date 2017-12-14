/*
 * 版权所有 (C) 2015 知启蒙(ZHIQIM) 保留所有权利。
 * 
 * 指定登记&发行网站： https://www.zhiqim.com/ 欢迎加盟知启蒙，[编程有你，知启蒙一路随行]。
 *
 * 本文采用《知启蒙登记发行许可证》，除非符合许可证，否则不可使用该文件！
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
package org.zhiqim.gitsolo.manage;

import java.util.List;

import org.zhiqim.gitsolo.Gitsolo;
import org.zhiqim.gitsolo.dbo.ZpmRepository;
import org.zhiqim.gitsolo.dbo.ZpmRepositoryEx;

import org.zhiqim.kernel.Global;
import org.zhiqim.git.Git;
import org.zhiqim.git.util.Gits;
import org.zhiqim.httpd.HttpRequest;
import org.zhiqim.httpd.context.extend.StdSwitchAction;
import org.zhiqim.manager.ZmrSessionUser;
import org.zhiqim.orm.ZTable;
import org.zhiqim.orm.ZView;
import org.zhiqim.orm.dbo.Selector;
import org.zhiqim.project.ZpmConstants;
import org.zhiqim.project.ZpmMemberDao;
import org.zhiqim.project.ZpmProjectDao;
import org.zhiqim.kernel.util.DateTimes;
import org.zhiqim.kernel.util.Ids;
import org.zhiqim.kernel.util.Validates;
import org.zhiqim.httpd.validate.ones.IsAlphaNumericUrlLen;
import org.zhiqim.httpd.validate.ones.IsIntegerValue;
import org.zhiqim.httpd.validate.ones.IsLen;
import org.zhiqim.httpd.validate.ones.IsNotEmpty;

/**
 * 仓库管理，支持仓库的创建、删除和修改权限
 *
 * @version v1.0.0 @author zhichenggang 2014-3-21 新建与整理
 */
public class RepositoryAction extends StdSwitchAction implements ZpmConstants
{
    @Override
    protected void validateId(HttpRequest request)
    {
        request.addValidate(new IsNotEmpty("repositoryId", "请选择一个选项"));
    }

    @Override
    protected void validateForm(HttpRequest request)
    {
        request.addValidate(new IsAlphaNumericUrlLen("repositoryCode", "仓库编码必须为字母,数字或(.-_~)组合，[1, 32]范围", 1, 32));
        request.addValidate(new IsLen("repositoryName", "仓库名称不能为空，[1, 32]范围", 1, 32));
        request.addValidate(new IsIntegerValue("repositorySeq", "仓库排序必须是[0, 999999]范围的非负整数", 0, 999999));
        request.addValidate(new IsNotEmpty("repositoryUpdateRole", "请选择仓库提交角色"));
        request.addValidate(new IsNotEmpty("repositoryCommitRole", "请选择仓库提交角色"));
    }

    @Override
    protected void list(HttpRequest request) throws Exception
    {
        Selector selector = new Selector();
        selector.addMust("projectId", ZpmProjectDao.getProjectId(request));
        selector.addOrderbyAsc("repositorySeq");
        List<ZpmRepositoryEx> list = Global.get(ZView.class).list(ZpmRepositoryEx.class, selector);

        request.setAttribute("list", list);
    }
    
    @Override
    protected void add(HttpRequest request) throws Exception
    {
    }

    @Override
    protected void modify(HttpRequest request) throws Exception
    {
        long repositoryId = request.getParameterLong("repositoryId");
        ZpmRepository item = Global.get(ZTable.class).item(ZpmRepository.class, repositoryId);
        if(item == null)
        {
            request.returnHistory("仓库不存在，请重新选择");
            return;
        }
        
        if (!ZpmProjectDao.chkProjectId(request, item.getProjectId()))
        {//判断是否切换了默认项目
            return;
        }
        
        request.setAttribute("item", item);
    }

    @Override
    protected void insert(HttpRequest request) throws Exception
    {
        ZmrSessionUser sessionUser = request.getSessionUser(ZmrSessionUser.class);
        long projectId = sessionUser.getOperatorParamLong(FPM_PROJECT_ID_KEY);
        
        String repositoryCode = request.getParameter("repositoryCode");
        String repositoryName = request.getParameter("repositoryName");
        int repositorySeq = request.getParameterInt("repositorySeq");
        String repositoryUpdateRole = request.getParameter("repositoryUpdateRole");
        String repositoryCommitRole = request.getParameter("repositoryCommitRole");
        
        if (!Validates.isFileName(repositoryCode))
        {
            request.returnHistory("仓库编码不合法，请重选一个编码");
            return;
        }
        
        Selector selector = new Selector();
        selector.addMust("projectId", projectId);
        selector.addMust("repositoryCode", repositoryCode);
        if(Global.get(ZTable.class).count(ZpmRepository.class, selector) > 0)
        {
            request.returnHistory("仓库编码["+repositoryCode+"]已存在，请重选一个编码");
            return;
        }
        
        String repositoryPath = Gitsolo.getRepositoryPath(projectId, repositoryCode);
        Git git = Gits.create(repositoryPath, sessionUser.getOperatorCode(), sessionUser.getOperatorCode()+"@gitsolo.com");
        if(git == null)
        {
            request.returnHistory("仓库创建文件存储["+repositoryPath+"]时失败，请联系管理检查");
            return;
        }
        
        String time = DateTimes.getDateTimeString();
        ZpmRepository repo = new ZpmRepository();
        repo.setRepositoryId(Ids.longId());
        repo.setProjectId(projectId);
        repo.setRepositoryCode(repositoryCode);
        repo.setRepositoryName(repositoryName);
        repo.setRepositorySeq(repositorySeq);
        repo.setRepositoryUpdateRole(repositoryUpdateRole);
        repo.setRepositoryCommitRole(repositoryCommitRole);
        repo.setRepositoryCreator(sessionUser.getOperatorCode());
        repo.setRepositoryCreated(time);
        repo.setRepositoryModified(time);
        
        Global.get(ZTable.class).insert(repo);
        
        ZpmMemberDao.report(projectId, request.getSessionName(), "创建了仓库", repositoryName);
    }

    @Override
    protected void update(HttpRequest request) throws Exception
    {
        long repositoryId = request.getParameterLong("repositoryId");
        ZpmRepository item = Global.get(ZTable.class).item(ZpmRepository.class, repositoryId);
        if(item == null)
        {
            request.returnHistory("仓库不存在，请重新选择");
            return;
        }
        
        if (!ZpmProjectDao.chkProjectId(request, item.getProjectId()))
        {//判断是否切换了默认项目
            return;
        }
        
        String repositoryName = request.getParameter("repositoryName");
        String repositoryUpdateRole = request.getParameter("repositoryUpdateRole");
        String repositoryCommitRole = request.getParameter("repositoryCommitRole");
        int repositorySeq = request.getParameterInt("repositorySeq");
        
        item.setRepositoryName(repositoryName);
        item.setRepositoryUpdateRole(repositoryUpdateRole);
        item.setRepositoryCommitRole(repositoryCommitRole);
        item.setRepositorySeq(repositorySeq);
        item.setRepositoryModified(DateTimes.getDateTimeString());
        Global.get(ZTable.class).update(item);
        
        ZpmMemberDao.report(ZpmProjectDao.getProjectId(request), request.getSessionName(), "修改了仓库", repositoryName);
    }

    @Override
    protected void delete(HttpRequest request) throws Exception
    {
        long repositoryId = request.getParameterLong("repositoryId");
        ZpmRepository item = Global.get(ZTable.class).item(ZpmRepository.class, repositoryId);
        if(item == null)
        {
            request.returnHistory("仓库不存在，请重新选择");
            return;
        }
        
        if (!ZpmProjectDao.chkProjectId(request, item.getProjectId()))
        {//判断是否切换了默认项目
            return;
        }
        
        Gits.delete(Gitsolo.getRepositoryPath(item.getProjectId(), item.getRepositoryCode()));
        Global.get(ZTable.class).delete(ZpmRepository.class, repositoryId);
        
        ZpmMemberDao.report(ZpmProjectDao.getProjectId(request), request.getSessionName(), "删除了仓库", item.getRepositoryName());
    }
}
