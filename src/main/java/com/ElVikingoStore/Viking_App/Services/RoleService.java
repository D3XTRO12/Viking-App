package com.ElVikingoStore.Viking_App.Services;

import com.ElVikingoStore.Viking_App.DTOs.RoleDto;
import com.ElVikingoStore.Viking_App.Models.Role;
import com.ElVikingoStore.Viking_App.Repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public List<RoleDto> getAllRoles() {
        return roleRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RoleDto> getRoleById(UUID id) {
        return roleRepo.findById(id).map(this::convertToDTO);
    }

    public RoleDto createRole(RoleDto roleDto) {
        Role role = convertToEntity(roleDto);
        Role savedRole = roleRepo.save(role);
        return convertToDTO(savedRole);
    }

    public Optional<RoleDto> updateRole(UUID id, RoleDto roleDto) {
        return roleRepo.findById(id)
                .map(role -> {
                    role.setDescripcion(roleDto.getDescripcion());
                    role.setPermission(roleDto.getPermission());
                    return convertToDTO(roleRepo.save(role));
                });
    }

    public void deleteRole(UUID id) {
        roleRepo.deleteById(id);
    }

    private RoleDto convertToDTO(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setDescripcion(role.getDescripcion());
        dto.setPermission(role.getPermission());
        return dto;
    }

    private Role convertToEntity(RoleDto dto) {
        Role role = new Role();
        role.setDescripcion(dto.getDescripcion());
        role.setPermission(dto.getPermission());
        return role;
    }
}

