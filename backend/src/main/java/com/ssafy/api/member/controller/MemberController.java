package com.ssafy.api.member.controller;

import com.ssafy.api.member.dto.request.MemberInfoChangeRequestDto;
import com.ssafy.dto.MessageDto;
import com.ssafy.api.member.dto.request.MemberDuplicateRequestDto;
import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
  private final MemberService memberService;

  /**
   * [member-2] 회원가입 요청 시 해당 정보를 DB에 저장한다.
   * privilege : ALL
   * @return [200] 정상 등록완료
   */
  @PostMapping
  public ResponseEntity<MessageDto> registMember(@RequestBody MemberRegistRequestDto dto) throws Exception {

      // 회원 등록(service)
      memberService.regist(dto);

      // 정상 등록완료(200)
      return new ResponseEntity<>(new MessageDto("Member registered successfully"), HttpStatus.CREATED);
  }

    /**
     * [member-6] 회원정보 수정 시 입력한 비밀번호를 검증한 후, 회원정보 변경
     * privilege : COAME
     * @return [200] 정상 수정완료
     */
    @PatchMapping("/{longId}")
    public ResponseEntity<MessageDto> modifyMember(
            @PathVariable(value = "longId") Long longId,
            @RequestBody MemberInfoChangeRequestDto dto) throws Exception {

        // 회원정보 수정(service)
        memberService.modify(longId, dto);

        // 정상 수정완료(200)
        return new ResponseEntity<>(new MessageDto("Member modified successfully"), HttpStatus.OK);
    }

  /**
   * [member-14] 회원 ID를 중복검사한다.
   * privilege : ALL
   * @return [200] 사용 가능한 아이디
   */

  @PostMapping("/duplicate/id")
  public ResponseEntity<MessageDto> duplicateMember(@Validated @RequestBody MemberDuplicateRequestDto dto) throws Exception {

      // 중복여부 확인(service)
      boolean duplicated = memberService.isDuplicated(dto.getStringId());

      // 중복 되었을 때 client 오류(409)
      if (duplicated) return new ResponseEntity<>(
              new MessageDto("Member is duplicated"), HttpStatus.CONFLICT);

      // 중복 안되었을 때 사용 가능한 아이디(200)
      return new ResponseEntity<>(
              new MessageDto("Member is not duplicated, Usable id."), HttpStatus.OK);

  }
}
////  // 프로필 사진 등록 api
////  @PostMapping("/profiles/images/{id}")
////  public ResponseEntity<Map<String, String>> uploadProfile(@RequestParam("file") MultipartFile file, @PathVariable("id") String memberId) {
////    log.debug("Upload Profile Image : {}", file);
////    log.debug("Upload MemberId : {}", memberId);
////
////    List<MultipartFile> fileList = new ArrayList<>();
////    fileList.add(file);
////    fileService.uploadFileList(fileList, memberId, "profile");
////
////    Map<String, String> responseMap = new HashMap<>();
////    responseMap.put("message", "Success!");
////
////    return ResponseEntity.ok(responseMap);
////  }
////
////
////  // 프로필 사진 조회 api
////  @GetMapping("/profiles/images/{id}")
////  public ResponseEntity<Map<String, String>> getProfiles(@PathVariable("id") String memberId) {
////
////    String readImageUri = fileService.getProfile(memberId);
////    log.info("Read Profile Image : file uri - {}", readImageUri);
////
////    Map<String, String> responseMap = new HashMap<>();
////    responseMap.put("profileImageUrl", readImageUri);
////
////    return ResponseEntity.ok(responseMap);
////  }
////
////  @DeleteMapping("/profiles/images/{id}")
////  public ResponseEntity<Map<String, String>> deleteFile(@PathVariable("id") String memberId) {
////    log.info("Delete Profile Image : {}", memberId);
////    fileService.deleteProfile(memberId);
////
////    Map<String, String> responseMap = new HashMap<>();
////    responseMap.put("message", "Success!");
////
////    return ResponseEntity.ok(responseMap);
////  }
//
//  //Email과 name의 일치여부를 check하는 컨트롤러
////  @GetMapping("/check/findPw")
////  public @ResponseBody Map<String, Boolean> pw_find(String userEmail, String userName){
////    Map<String,Boolean> json = new HashMap<>();
////    boolean pwFindCheck = userService.userEmailCheck(userEmail,userName);
////
////    System.out.println(pwFindCheck);
////    json.put("check", pwFindCheck);
////    return json;
////  }
//
//  //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
//  @PostMapping("/passwords")
//  public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String memberId, @RequestParam String email) {
//    try {
////      findPwService.requestResetPassword(memberId, email);
//      Map<String, String> responseMap = new HashMap<>();
//      responseMap.put("message", "Reset password email sent successfully");
//      return new ResponseEntity<>(responseMap, HttpStatus.OK);
//    } catch (Exception e) {
//      e.printStackTrace();
//
//      // 에러 응답
//      Map<String, String> errorMap = new HashMap<>();
//      errorMap.put("error", "Error sending reset password email");
//      return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }
////  @PutMapping("/reset")
////  public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String newPassword) {
////    try {
////      resetPasswordService.resetPassword(username, newPassword);
////      return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
////    } catch (Exception e) {
////      e.printStackTrace();
////      return new ResponseEntity<>("Error resetting password", HttpStatus.INTERNAL_SERVER_ERROR);
////    }
////  }
////  public @ResponseBody void sendEmail(String userEmail, String userName){
////    MailDto dto = sendEmailService.createMailAndChangePassword(userEmail, userName);
////    sendEmailService.mailSend(dto);
////
////  }
//
////  id : String(max : 20)
////  email : String(max : 50)
//
//  //회원정보 수정
////  @PatchMapping("/{id}")
////  public ResponseEntity<String> UpdateMember(@PathVariable("id") Long memberId, @RequestBody UpdateMemberDto updateMemberDto) {
////    try {
////      memberService.updateMember(memberId, updateMemberDto);
////      return new ResponseEntity<>("Member information updated successfully", HttpStatus.OK);
////    } catch (Exception e) {
////      e.printStackTrace();
////      return new ResponseEntity<>("Error updating member information", HttpStatus.INTERNAL_SERVER_ERROR);
////    }
////  }
//}