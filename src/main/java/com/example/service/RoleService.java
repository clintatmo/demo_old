package com.example.service;


import com.example.model.Role;

/**

 * @author catmosoerodjo
 *
 */
public interface RoleService {

    Role findById(Long id);

    Role findByCode(String code);

}
