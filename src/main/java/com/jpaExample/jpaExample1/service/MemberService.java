package com.jpaExample.jpaExample1.service;

import com.jpaExample.jpaExample1.domain.member.Member;
import com.jpaExample.jpaExample1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // jpa조회하는 성능에서는 최적화 읽전용에서 사용
@RequiredArgsConstructor // 요즘은 이런식으로 사용
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    // readOnly true 경우 읽지 전용이므로 쓰기 전용에는 사용하지않음
    // 지금 현재는 조회가 많기 때문에 전체적으로 true 설정하고
    // 가입에만 따로 선언 이게 우선적으로 실행되기 때문에
    public Long join(Member member) {
        // 중복 회원 확인
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원전체조회
    public List<Member> findMembers() {
        return memberRepository.findByAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findByOne(id);
    }
}
