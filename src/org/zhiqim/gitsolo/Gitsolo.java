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

import java.io.IOException;
import java.io.OutputStream;

import org.zhiqim.kernel.constants.HttpConstants;
import org.zhiqim.git.constants.GitNameConstants;
import org.zhiqim.git.http.HttpHandler;
import org.zhiqim.git.util.GitStreams;
import org.zhiqim.httpd.HttpResponse;
import org.zhiqim.kernel.util.Longs;
import org.zhiqim.kernel.util.Strings;
import org.zhiqim.kernel.util.consts.Lng;

/**
 * Gitsolo工具类
 *
 * @version v1.0.0 @author zouzhigang 2017-11-27 新建与整理
 */
public class Gitsolo implements GitNameConstants, HttpConstants
{
    /** Gitsolo前缀名称 */
    public static final String GIT_PREFIX     = "/gitsolo";
    
    /**
     * 发送错误信息
     * 
     * @param response      HTTP响应
     * @param pack          上传或接受包
     * @param error         错误信息
     * @throws IOException  IO异常
     */
    public static void sendError(HttpResponse response, HttpHandler pack, String error) throws IOException
    {
        OutputStream out = response.getOutputStream();

        if (pack.hasSideBand())
            GitStreams.writeSideBandError(out, "error: "+error);
        else
            GitStreams.writeString(out, "error: "+error);
        
        response.setContentType(pack.getContentType());
        response.commit();
    }
    
    /**
     * 发送需要注验证信息的响应
     * 
     * @param response
     * @throws IOException
     */
    public static void sendUnauthorized(HttpResponse response) throws IOException
    {
        response.setHeader(_WWW_AUTHENTICATE_, "Basic realm=\"gitsolo\"");
        response.sendError(_401_UNAUTHORIZED_);
    }
    
    /**
     * 通过类型、仓库编码获取仓库路径，得到如/1710131047430003/fadfox.git的名称
     * 
     * @param projectId         项目编号
     * @param repositoryCode    仓库编码
     * @return
     */
    public static String getRepositoryPath(long projectId, String repositoryCode)
    {
        return "/" + projectId + "/" + repositoryCode + ".git";
    }
    

    /**
     * 通过访问路径获取仓库名称，得到如/1710131047430003/fadfox.git的名称
     * 
     * @param pathInContext    上下文中的路径/gitsolo/1710131047430003/fadfox.git/git-upload-pack
     * @return                 仓库名称如/1710131047430003/fadfox.git
     */
    public static String getRepositoryName(String pathInContext)
    {
        int ind = pathInContext.indexOf(_GIT);
        if (ind == -1)
            return null;
        
        return Strings.trimLeft(pathInContext.substring(0, ind+4), GIT_PREFIX);
    }
    
    /**
     * 通过仓库名称/1710131047430003/fadfox.git，得到{1710131047430003, fadfox}
     * 
     * @param repositoryName    仓库名称/organization/fadfox.git
     * @return                  Lng对象{1710131047430003, fadfox}
     */
    public static Lng getRepositoryCode(String repositoryName)
    {
        repositoryName = Strings.trim(repositoryName, "/", _GIT);
        String[] strs = repositoryName.split("/");
        
        return new Lng(Longs.toLong(strs[0]), strs[1]);
    }
}
