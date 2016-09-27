package com.example.activiti.identity;

import com.example.config.CustomUserDetailsService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.AbstractManager;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**

 * @author catmosoerodjo
 *
 */
public class ActivitiGroupManager extends AbstractManager implements GroupIdentityManager {
    
    @Autowired
    private CustomUserDetailsService userService;

    @Override
    public List<Group> findGroupsByUser(String username) {
        Collection<? extends GrantedAuthority> authorities = userService.loadUserByUsername(username).getAuthorities();
        List<Group> groups = new ArrayList<>();

        for(GrantedAuthority authority: authorities) {

            groups.add(authorityToActivitiGroup(authority));
        }

        return groups;
    }

    @Override
    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        return null;
    }

    @Override
    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        return 0;
    }

    @Override
    public boolean isNewGroup(Group group) {
        return false;
    }

    public static GroupEntity authorityToActivitiGroup(GrantedAuthority authority) {
        GroupEntity group = new GroupEntity();
        group.setName(authority.getAuthority());
        group.setType("");
        return group;
    }

    @Override
    public Group createNewGroup(String groupId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertGroup(Group group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateGroup(Group updatedGroup) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GroupQuery createNewGroupQuery() {
        return new GroupQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutor());
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        return null;
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        return 0;
    }

}
