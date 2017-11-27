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
package org.zhiqim.gitsolo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.zhiqim.git.objer.model.PersonIdent;
import org.zhiqim.git.objer.objectid.RevCommit;
import org.zhiqim.kernel.util.Strings;

/**
 * 提交日志模型，对RevCommit进行封装解析
 *
 * @version v1.0.0 @author zouzhigang 2016-11-1 新建与整理
 */
public class CommitModel implements Serializable, Comparable<CommitModel>
{
    private static final long serialVersionUID = 1L;

    public final String repository;
    public final String branch;
    private final RevCommit commit;

    public CommitModel(String repository, String branch, RevCommit commit)
    {
        this.repository = repository;
        this.branch = branch;
        this.commit = commit;
    }

    public RevCommit getCommit()
    {
        return commit;
    }

    public String getName()
    {
        return commit.getName();
    }

    public String getShortName()
    {
        return commit.getName().substring(0, 8);
    }

    public String getShortMessage()
    {
        return commit.getShortMessage();
    }

    public Date getCommitDate()
    {
        return new Date(commit.getCommitTime() * 1000L);
    }

    public int getParentCount()
    {
        return commit.getParentCount();
    }

    public List<RevCommit> getParents()
    {
        return commit.getParents();
    }

    public PersonIdent getAuthorIdent()
    {
        return commit.getAuthorIdent();
    }

    public PersonIdent getCommitterIdent()
    {
        return commit.getCommitterIdent();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof CommitModel)
        {
            CommitModel commit = (CommitModel) o;
            return repository.equals(commit.repository) && getName().equals(commit.getName());
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return (repository + commit).hashCode();
    }

    @Override
    public int compareTo(CommitModel o)
    {
        if (commit.getCommitTime() > o.commit.getCommitTime())
            return -1;
        else if (commit.getCommitTime() < o.commit.getCommitTime())
            return 1;
        else
            return 0;
    }

    @Override
    public String toString()
    {
        return Strings.formatMessage("{0} {1} {2,date,yyyy-MM-dd HH:mm} {3} {4}", 
            getShortName(), branch, 
            getCommitterIdent().getWhen(), 
            getAuthorIdent().getName(), 
            getShortMessage());
    }
}