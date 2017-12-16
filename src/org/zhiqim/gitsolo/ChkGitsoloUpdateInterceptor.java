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

import java.util.List;

import org.zhiqim.git.Git;
import org.zhiqim.git.GitConstants;
import org.zhiqim.git.util.Gits;
import org.zhiqim.gitsolo.dbo.ZpmRepository;
import org.zhiqim.httpd.HttpRequest;
import org.zhiqim.httpd.HttpResponse;
import org.zhiqim.httpd.context.core.Interceptor;
import org.zhiqim.kernel.Global;
import org.zhiqim.kernel.annotation.AnAlias;
import org.zhiqim.kernel.constants.ZhiqimConstants;
import org.zhiqim.kernel.json.Jsons;
import org.zhiqim.kernel.util.Lists;
import org.zhiqim.kernel.util.Strings;
import org.zhiqim.kernel.util.Validates;
import org.zhiqim.kernel.util.codes.Base64;
import org.zhiqim.kernel.util.consts.Lng;
import org.zhiqim.manager.dao.ZmrOperatorDao;
import org.zhiqim.manager.dbo.ZmrOperator;
import org.zhiqim.orm.ZTable;
import org.zhiqim.orm.dbo.Selector;
import org.zhiqim.project.dbo.ZpmMember;

/**
 * 检查仓库是否存在拦截器
 *
 * @version v1.0.0 @author zouzhigang 2016-10-24 新建与整理
 */
@AnAlias("chkGitsoloUpdate")
public class ChkGitsoloUpdateInterceptor implements Interceptor, ZhiqimConstants, GitConstants
{
    @Override
    public void intercept(HttpRequest request) throws Exception
    {
        intercept(request, true);
    }
    
    /**
     * 拦截检查
     * 
     * @param request       请求对象
     * @param isUpdate      是更新拦截还是提交拦截
     * @throws Exception    异常
     */
    public static void intercept(HttpRequest request, boolean isUpdate) throws Exception
    {
        HttpResponse response = request.getResponse();
        
        //1.要求客户端版本在1.8.0以上 TODO JGit的版本暂时处理
        String gitClientVersion = request.getHeader(_USER_AGENT_);
        if (gitClientVersion == null || (gitClientVersion.startsWith("git/") && gitClientVersion.compareTo(GIT_MIN_CLIENT_VERSION) < 0))
        {
            response.sendError(_403_FORBIDDEN_, "Git客户端版本必须"+GIT_MIN_CLIENT_VERSION+"以上");
            return;
        }
        
        String repositoryName = Gitsolo.getRepositoryName(request.getPathInContext());
        if (Validates.isEmpty(repositoryName))
        {//仓库名称
            response.sendError(_400_BAD_REQUEST_);
            return;
        }
        
        Lng repoCode = Gitsolo.getRepositoryCode(repositoryName);
        Selector selector = new Selector();
        selector.addMust("projectId", repoCode.value());
        selector.addMust("repositoryCode", repoCode.desc());
        ZpmRepository repo = Global.get(ZTable.class).item(ZpmRepository.class, selector);
        if (repo == null)
        {
            response.sendError(_404_NOT_FOUND_, "您访问的仓库["+repositoryName+"]不存在");
            return;
        }
        
        if (!authorize(request, repo, isUpdate))
        {//要求认证但认证失败
            return;
        }
        
        Git git = Gits.git(repositoryName);
        if (git == null)
        {//仓库
            response.sendError(_404_NOT_FOUND_, "您访问的仓库["+repositoryName+"]不存在");
            return;
        }
        
        //把仓库对象放置到属性表中
        request.setAttribute(GIT_ATTRIBUTE_REPOSITORY, git);
    }
    
    /**
     * 验证用户权限
     * 
     * @param request       请求对应
     * @param repo          仓库对象
     * @param isUpdate      是更新拦截还是提交拦截
     * @return              =true表示用户权限认证通过
     * @throws Exception
     */
    private static boolean authorize(HttpRequest request, ZpmRepository repo, boolean isUpdate) throws Exception
    {
        //1.读取认证字符串
        String authorization = request.getHeader(_AUTHORIZATION_);
        if (Validates.isEmptyBlank(authorization) || !Strings.startsWithIgnoreCase(authorization, "Basic"))
        {
            Gitsolo.sendUnauthorized(request.getResponse());
            return false;
        }
      
        //2.解析用户名称密码并验证
        String base64Credentials = Strings.trim(authorization.substring("Basic".length()));
        try
        {
            String credentials = Base64.decodeUTF8(base64Credentials);
            String[] values = credentials.split(":", 2);
            if (values.length != 2)
            {
                Gitsolo.sendUnauthorized(request.getResponse());
                return false;
            }
          
            String operatorCode = values[0];
            String operatorPass = values[1];

            //3.检查操作员是否存在、是否有权限、是否密码正确
            Selector selector = new Selector();
            selector.addMust("projectId", repo.getProjectId());
            selector.addMust("operatorCode", operatorCode);
            ZpmMember member = Global.get(ZTable.class).item(ZpmMember.class, selector);
            if (member == null)
            {//3.1 如果不是成员
                Gitsolo.sendUnauthorized(request.getResponse());
                return false;
            }
            
            if (member.getMemberType() != 0 && !isRole(member.getMemberRole(), isUpdate?repo.getRepositoryUpdateRole():repo.getRepositoryCommitRole()))
            {//3.2 成员权限未匹配
                Gitsolo.sendUnauthorized(request.getResponse());
                return false;
            }
            
            ZmrOperator operator = Global.get(ZTable.class).item(ZmrOperator.class, operatorCode);
            if (operator == null || operator.getOperatorStatus() != 0)
            {//3.3 操作员不存在或停用
                Gitsolo.sendUnauthorized(request.getResponse());
                return false;
            }
            
            String secret = Jsons.getString(operator.getOperatorParam(), Gitsolo.GITSOLO_SECRET_KEY);
            if (Validates.isEmptyBlank(secret))
            {
                if (!ZmrOperatorDao.validatePassword(operator, operatorPass))
                {//3.4 密码不正确
                    Gitsolo.sendUnauthorized(request.getResponse());
                    return false;
                }
            }
            else
            {
                if (!secret.equals(operatorPass))
                {//3.4 独立密码不正常
                    Gitsolo.sendUnauthorized(request.getResponse());
                    return false;
                }
            }
            
            //把操作员信息放置到属性表中
            request.setRequestName(operatorCode);
            request.setAttribute(GIT_ATTRIBUTE_OPERATOR, operator);
            return true;
        }
        catch(Exception e)
        {
            Gitsolo.sendUnauthorized(request.getResponse());
            return false;
        } 
    }
    
    /**
     * 判断成员角色是否是仓库角色
     * 
     * @param memberRole        成员角色
     * @param repositoryRole    仓库角色
     * @return                  =true表示成员有权限
     */
    private static boolean isRole(String memberRole, String repositoryRole)
    {
        if (Validates.isEmptyBlank(repositoryRole))
        {//只允许组长访问
            return false;
        }
        
        if (Validates.isEmptyBlank(memberRole))
        {//成员没有任何权限
            return false;
        }
        
        List<String> mRoleList = Lists.toStringList(memberRole);
        List<String> rRoleList = Lists.toStringList(repositoryRole);
        
        for (String role : mRoleList)
        {//仓库角色只要有一个成员角色即通过
            if (rRoleList.contains(role))
                return true;
        }
        
        return false;
    }
}
