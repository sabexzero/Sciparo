package com.example.rockpaperscissorsultimate.services;

import com.example.rockpaperscissorsultimate.models.Player;
import com.example.rockpaperscissorsultimate.models.Role;
import com.example.rockpaperscissorsultimate.repositories.RoleRepository;
import com.example.rockpaperscissorsultimate.utils.dtos.CreateRoleRequest;
import com.example.rockpaperscissorsultimate.utils.exceptions.Role.FailedToCreateRoleException;
import com.example.rockpaperscissorsultimate.utils.exceptions.Role.RoleNotFoundException;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PlayerService playerService;
    
    public Role createRole(@NotNull CreateRoleRequest request){
        
        Role newRole = Role.builder()
                .title(request.getTitle())
                .roleImportance(request.getRoleImportance())
                .build();
        
        try{
            return roleRepository.save(newRole);
        }catch (Exception exception){
            throw new FailedToCreateRoleException("An error occurred when registering a role");
        }
        
    }
    
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
    
    public Role getRoleById(UUID id){
        var role = roleRepository.findById(id).orElse(null);
        if(role == null)
            throw new RoleNotFoundException("The Role was not found");
        return role;
    }
    
    public Role getRoleByName(String findName){
        var role = roleRepository.findByTitle(findName);
        if(role == null)
            throw new RoleNotFoundException("The Role was not found");
        return role;
    }
    
    public void updateRole(Role entity){
        roleRepository.save(entity);
    }
    
    public void setPlayerRole(@NotNull Player player, @NotNull Role role){
        player.setRole(role);
        playerService.updatePlayer(player);
    }
    
    public void deleteRoleById(UUID id){
        roleRepository.deleteById(id);
    }
}
