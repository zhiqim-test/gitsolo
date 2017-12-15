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
package org.zhiqim.gitsolo.manage;

import org.zhiqim.gitsolo.dbo.ZpmRepository;

import org.zhiqim.httpd.HttpRequest;
import org.zhiqim.httpd.context.extend.StdSwitchAction;
import org.zhiqim.httpd.validate.ones.IsNotEmpty;
import org.zhiqim.kernel.Global;
import org.zhiqim.manager.dao.ZmrOperatorDao;
import org.zhiqim.orm.ZTable;

public class PasswordSetAction extends StdSwitchAction
{

    @Override
    protected void validateId(HttpRequest request)
    {
        request.addValidate(new IsNotEmpty("repositoryId", "请选择一个选项"));
    }

    @Override
    protected void validateForm(HttpRequest request)
    {
    }

    @Override
    protected void list(HttpRequest request) throws Exception
    {
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
        request.setAttribute("item", item);
    }

    @Override
    protected void insert(HttpRequest request) throws Exception
    {
        

    }

    @Override
    protected void update(HttpRequest request) throws Exception
    {
        long repositoryId = request.getParameterLong("repositoryId");
        ZpmRepository item = Global.get(ZTable.class).item(ZpmRepository.class, repositoryId);
        String repositoryName = request.getParameter("repositoryName");
        String repositoryPassword = request.getParameter("repositoryPassword");
        
        ZmrOperatorDao.addOrUpdateOperatorParam(item.getRepositoryCreator(), repositoryName, repositoryPassword);
    }

    @Override
    protected void delete(HttpRequest request) throws Exception
    {
    }

}
