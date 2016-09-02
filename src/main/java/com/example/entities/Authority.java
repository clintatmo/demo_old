package com.example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Authority implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8603421331921819156L;

	@Id
	@Column(name="authority_id")
	private Long authorityId;
	
    @Column(name="authority")
    private String authority;

	@Column(name = "ACTIVE")
	private boolean active;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority",
			joinColumns = { @JoinColumn(name = "authority_id") },
			inverseJoinColumns = { @JoinColumn(name = "user_id") })
	@JsonBackReference
	private Set<User> users;
    
	public Long getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}
	public String getAuthority() {
        return this.authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}