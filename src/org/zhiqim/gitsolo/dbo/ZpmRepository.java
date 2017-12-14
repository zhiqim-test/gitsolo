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
package org.zhiqim.gitsolo.dbo;

import java.io.Serializable;

import org.zhiqim.kernel.annotation.AnAlias;
import org.zhiqim.kernel.json.Jsons;
import org.zhiqim.orm.annotation.*;

/**
 * 项目代码仓库表 对应表《ZPM_REPOSITORY》
 */
@AnAlias("ZpmRepository")
@AnTable(table="ZPM_REPOSITORY", key="REPOSITORY_ID", type="InnoDB")
public class ZpmRepository implements Serializable
{
    private static final long serialVersionUID = 1L;

    @AnTableField(column="PROJECT_ID", type="long", notNull=true)    private long projectId;    //1.项目编号
    @AnTableField(column="REPOSITORY_ID", type="long", notNull=true)    private long repositoryId;    //2.仓库编号
    @AnTableField(column="REPOSITORY_CODE", type="string,32", notNull=true)    private String repositoryCode;    //3.仓库编码
    @AnTableField(column="REPOSITORY_NAME", type="string,64", notNull=true)    private String repositoryName;    //4.仓库名称
    @AnTableField(column="REPOSITORY_STATUS", type="byte", notNull=true)    private int repositoryStatus;    //5.仓库状态，0：正常，1：停用
    @AnTableField(column="REPOSITORY_SEQ", type="int", notNull=true)    private int repositorySeq;    //6.仓库排序，整型，从小到大
    @AnTableField(column="REPOSITORY_UPDATE_ROLE", type="string,64", notNull=false)    private String repositoryUpdateRole;    //7.仓库角色，为空表示全支持，不为空多个角色用逗号隔开，如manage,test
    @AnTableField(column="REPOSITORY_PASSWORD", type="string,32", notNull=false)    private String repositoryPassword;    //8.代码仓库密钥
    @AnTableField(column="REPOSITORY_COMMIT_ROLE", type="string,64", notNull=false)    private String repositoryCommitRole;    //9.仓库角色，为空表示全支持，不为空多个角色用逗号隔开，如manage,test
    @AnTableField(column="REPOSITORY_CREATOR", type="string,32", notNull=true)    private String repositoryCreator;    //10.仓库创建者
    @AnTableField(column="REPOSITORY_CREATED", type="string,19,char", notNull=true)    private String repositoryCreated;    //11.仓库创建时间
    @AnTableField(column="REPOSITORY_MODIFIED", type="string,19,char", notNull=true)    private String repositoryModified;    //12.仓库更新时间

    public String toString()
    {
        return Jsons.toString(this);
    }

    public long getProjectId()
    {
        return projectId;
    }

    public void setProjectId(long projectId)
    {
        this.projectId = projectId;
    }

    public long getRepositoryId()
    {
        return repositoryId;
    }

    public void setRepositoryId(long repositoryId)
    {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryCode()
    {
        return repositoryCode;
    }

    public void setRepositoryCode(String repositoryCode)
    {
        this.repositoryCode = repositoryCode;
    }

    public String getRepositoryName()
    {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName)
    {
        this.repositoryName = repositoryName;
    }

    public int getRepositoryStatus()
    {
        return repositoryStatus;
    }

    public void setRepositoryStatus(int repositoryStatus)
    {
        this.repositoryStatus = repositoryStatus;
    }

    public int getRepositorySeq()
    {
        return repositorySeq;
    }

    public void setRepositorySeq(int repositorySeq)
    {
        this.repositorySeq = repositorySeq;
    }

    public String getRepositoryUpdateRole()
    {
        return repositoryUpdateRole;
    }

    public void setRepositoryUpdateRole(String repositoryUpdateRole)
    {
        this.repositoryUpdateRole = repositoryUpdateRole;
    }

    public String getRepositoryPassword()
    {
        return repositoryPassword;
    }

    public void setRepositoryPassword(String repositoryPassword)
    {
        this.repositoryPassword = repositoryPassword;
    }

    public String getRepositoryCommitRole()
    {
        return repositoryCommitRole;
    }

    public void setRepositoryCommitRole(String repositoryCommitRole)
    {
        this.repositoryCommitRole = repositoryCommitRole;
    }

    public String getRepositoryCreator()
    {
        return repositoryCreator;
    }

    public void setRepositoryCreator(String repositoryCreator)
    {
        this.repositoryCreator = repositoryCreator;
    }

    public String getRepositoryCreated()
    {
        return repositoryCreated;
    }

    public void setRepositoryCreated(String repositoryCreated)
    {
        this.repositoryCreated = repositoryCreated;
    }

    public String getRepositoryModified()
    {
        return repositoryModified;
    }

    public void setRepositoryModified(String repositoryModified)
    {
        this.repositoryModified = repositoryModified;
    }

}
