package com.example.shoppingMall.configuration;

import com.example.shoppingMall.dto.request.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                String email = tokenProvider.getEmailFromJWT(jwt);
                // 4. Khởi tạo đối tượng UserPrincipal của bạn với các thông tin trên
                UserPrincipal userPrincipal = new UserPrincipal(userId, email);

                // 5. Tạo đối tượng Authentication của Spring Security
                // Tham số đầu tiên CHÍNH LÀ đối tượng sẽ được map vào @AuthenticationPrincipal ở Controller
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userPrincipal,null,null);

                // Lưu thông tin chi tiết về Request (IP, Session...) vào Authentication nếu cần
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. QUAN TRỌNG NHẤT: Đặt thông tin xác thực này vào SecurityContext
                // Từ giây phút này, Spring Security công nhận User này đã đăng nhập hợp lệ
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        // Cho phép request tiếp tục đi tiếp sang các filter khác hoặc vào Controller
        filterChain.doFilter(request, response);
    }

    // Hàm bổ trợ để bóc tách chuỗi Token từ Header "Authorization: Bearer <Token>"
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Cắt bỏ chữ "Bearer " để lấy chuỗi Token nguyên bản
        }
        return null;
    }
}