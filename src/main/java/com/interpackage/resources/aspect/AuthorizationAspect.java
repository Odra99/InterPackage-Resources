package com.interpackage.resources.aspect;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class AuthorizationAspect {

    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(requiredRole)")
    public Object checkPermissions(ProceedingJoinPoint joinPoint, RequiredRole requiredRole) throws Throwable {
        String rolesHeader = request.getHeader("roles");
        Set<String> roles = new HashSet<>(Arrays.asList(rolesHeader.split(",")));
        Set<String> requiredRoles = new HashSet<>(Arrays.asList(requiredRole.value()));
        
        

        // Verificar si al menos un rol coincide
        requiredRoles.retainAll(roles);
        

        if (!requiredRoles.isEmpty()) {
            return joinPoint.proceed();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new HashMap<String, Object>() {{
                        put("mensaje", "No tienes permisos suficientes para realizar esta acción.");
                        put("estado", HttpStatus.FORBIDDEN.value());
                    }}
            );
        }
    }
}