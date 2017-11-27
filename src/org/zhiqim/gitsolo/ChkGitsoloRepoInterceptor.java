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
package org.zhiqim.gitsolo;

import java.util.ArrayList;
import java.util.List;

import org.zhiqim.gitsolo.dbo.ZpmRepository;

import org.zhiqim.kernel.annotation.AnAlias;
import org.zhiqim.kernel.Global;
import org.zhiqim.git.Git;
import org.zhiqim.git.GitConstants;
import org.zhiqim.git.refer.Ref;
import org.zhiqim.git.util.Gits;
import org.zhiqim.httpd.HttpRequest;
import org.zhiqim.httpd.context.core.Interceptor;
import org.zhiqim.manager.ZmrSessionUser;
import org.zhiqim.orm.ZTable;
import org.zhiqim.project.ZpmConstants;
import org.zhiqim.kernel.util.Validates;

@AnAlias("chkGitsoloRepo")
public class ChkGitsoloRepoInterceptor implements Interceptor, GitConstants, ZpmConstants
{
    @Override
    public void intercept(HttpRequest request) throws Exception
    {
        long repositoryId = request.getParameterLong("repositoryId");
        ZpmRepository repo = Global.get(ZTable.class).item(ZpmRepository.class, repositoryId);
        if(repo == null)
        {
            request.setRedirect("repository.htm", "该仓库没在数据库中");
            return;
        }
        
        ZmrSessionUser sessionUser = request.getSessionUser(ZmrSessionUser.class);
        long projectId = sessionUser.getOperatorParamLong(FPM_PROJECT_ID_KEY);
        if (repo.getProjectId() != projectId)
        {//切换了项目
            request.setRedirect("repository.htm");
            return;
        }
        
        String repositoryName = Gitsolo.getRepositoryPath(repo.getProjectId(), repo.getRepositoryCode());
        Git git =  Gits.git(repositoryName);
        if(git == null)
        {
            request.setRedirect("repository.htm", "该Git仓库未找到");
            return;
        }
        
        request.addParam("repositoryId", ""+repositoryId);
        request.setAttribute("repositoryName", repositoryName);
        request.setAttribute("ZpmRepository", repo);
        request.setAttribute(GIT_ATTRIBUTE_REPOSITORY, git);
        
        //把分枝和标签列表显示出来
        String head = request.getParameter("head");
        if (Validates.isEmpty(head))
            head = "master";
        
        List<String> headList = new ArrayList<>();
        
        headList.add("分枝");
        List<Ref> refList = git.branchList().call();
        for (Ref ref : refList)
        {
            String brancheName = ref.getName().substring(P_REFS_HEADS_.length());
            headList.add(brancheName);
        }
        
        headList.add("标签");
        List<Ref> tRefList = git.tagList().call();
        for (Ref ref : tRefList)
        {
            String tagName = ref.getName().substring(P_REFS_TAGS_.length());
            headList.add(tagName);
        }
        
        //把选中的放置第一个位置
        headList.add(0, head);
        request.setAttribute("headList", headList);
    }
}
