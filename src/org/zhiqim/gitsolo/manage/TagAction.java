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

import java.util.ArrayList;
import java.util.List;

import org.zhiqim.gitsolo.model.TagModel;

import org.zhiqim.git.Git;
import org.zhiqim.git.GitConstants;
import org.zhiqim.git.objer.objectid.ObjectId;
import org.zhiqim.git.objer.objectid.RevCommit;
import org.zhiqim.git.refer.Ref;
import org.zhiqim.httpd.HttpRequest;
import org.zhiqim.httpd.context.extend.StdSwitchAction;
import org.zhiqim.project.ZpmMemberDao;
import org.zhiqim.project.ZpmProjectDao;
import org.zhiqim.httpd.validate.ones.IsAlphaNumericDotLen;

/**
 * 标签列表
 *
 * @version v1.0.0 @author zouzhigang 2016-11-1 新建与整理
 */
public class TagAction extends StdSwitchAction implements GitConstants
{
    @Override
    protected void validateId(HttpRequest request)
    {
        request.addValidate(new IsAlphaNumericDotLen("tagName", "标签名称为字母数字和点号组成，[1, 20]位", 1, 20));
    }
    
    @Override
    protected void validateForm(HttpRequest request)
    {
        request.addValidate(new IsAlphaNumericDotLen("tagName", "标签名称为字母数字和点号组成，[1, 20]位", 1, 20));
    }
    
    @Override
    protected void list(HttpRequest request) throws Exception
    {
        Git git = (Git)request.getAttribute(GIT_ATTRIBUTE_REPOSITORY);
        List<Ref> refList = git.tagList().call();
        
        List<TagModel> list = new ArrayList<>();
        for (Ref ref : refList)
        {
            ObjectId oid = ref.getObjectId();
            RevCommit commit = git.resolve(oid, RevCommit.class);
            list.add(new TagModel().setRef(ref).setCommit(commit));
        }
        
        request.setAttribute("list", list);
    }
    
    @Override
    protected void add(HttpRequest request) throws Exception
    {
    }
    
    @Override
    protected void insert(HttpRequest request) throws Exception
    {
        String tagName = request.getParameter("tagName");
        
        Git git = (Git)request.getAttribute(GIT_ATTRIBUTE_REPOSITORY);
        git.tag().setName(tagName).call();
        
        ZpmMemberDao.report(ZpmProjectDao.getProjectId(request), request.getSessionName(), "创建了标签", tagName);
    }
    
    @Override
    protected void modify(HttpRequest request) throws Exception
    {
    }

    @Override
    protected void update(HttpRequest request) throws Exception
    {
        String oldTagName = request.getParameter("oldTagName");
        String tagName = request.getParameter("tagName");
        if(oldTagName.equals(tagName))
            return;
        Git git = (Git)request.getAttribute(GIT_ATTRIBUTE_REPOSITORY);
        List<Ref> refList = git.tagList().call();
        for (Ref ref : refList)
        {
            String brancheName1 = ref.getName().substring(P_REFS_TAGS_.length());
            if(brancheName1.equals(tagName))
            {
                request.setResponseError("该标签已存在!");
                return;
            }
        }
        git.tagRename().setNewName(tagName).setOldName(oldTagName).call();
        
        ZpmMemberDao.report(ZpmProjectDao.getProjectId(request), request.getSessionName(), "修改了标签", tagName);
    }
    
    @Override
    protected void delete(HttpRequest request) throws Exception
    {
        String tagName = request.getParameter("tagName");
        Git git = (Git)request.getAttribute(GIT_ATTRIBUTE_REPOSITORY);
        git.tagDelete().setTags(tagName).call();
        
        ZpmMemberDao.report(ZpmProjectDao.getProjectId(request), request.getSessionName(), "删除了标签", tagName);
    }
}
