package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.admin.dto.AdminMemberDto;
import com.onepicklux.domain.admin.dto.PointDto;
import com.onepicklux.domain.admin.service.AdminMemberService;
import com.onepicklux.global.common.ApiResponse;
import com.onepicklux.domain.admin.dto.AdminMemberDto.PointHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping
    public ApiResponse<List<AdminMemberDto.MemberListResponse>> getAllMembers() {
        return ApiResponse.success(adminMemberService.getAllMembers());
    }

    @GetMapping("/{memberId}/360")
    public ApiResponse<AdminMemberDto.Member360Response> getMember360View(@PathVariable Long memberId) {
        return ApiResponse.success(adminMemberService.getMember360View(memberId));
    }

    @PatchMapping("/{memberId}/memo")
    public ApiResponse<String> updateAdminMemo(@PathVariable Long memberId, @RequestBody AdminMemberDto.UpdateMemoRequest request) {
        adminMemberService.updateAdminMemo(memberId, request);
        return ApiResponse.success("메모가 저장되었습니다.");
    }

    @PostMapping("/{memberId}/suspend")
    public ApiResponse<String> toggleMemberSuspend(@PathVariable Long memberId) {
        String resultMessage = adminMemberService.toggleMemberSuspend(memberId);
        return ApiResponse.success(resultMessage);
    }

    @PostMapping("/{memberId}/points")
    public ApiResponse<String> processPoint(@PathVariable Long memberId, @RequestBody PointDto.Request request) {
        adminMemberService.processAdminPoint(memberId, request);
        return ApiResponse.success("포인트 처리가 완료되었습니다.");
    }

    @GetMapping("/{memberId}/points/history")
    public ApiResponse<List<PointHistoryResponse>> getPointHistory(@PathVariable Long memberId) {
        return ApiResponse.success(adminMemberService.getMemberPointHistory(memberId));
    }
}