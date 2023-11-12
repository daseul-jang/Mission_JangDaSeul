package com.techit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class QuoteAppTest {
    @Test
    @DisplayName("종료하기")
    void t1() {
        Scanner sc = TestUtil.genScanner("""
                종료
                """.stripIndent());

        //new QuoteApp(sc).run();

        sc.close();
    }

    @Test
    @DisplayName("등록하기")
    void t2() {
        Scanner sc = TestUtil.genScanner("""
                등록
                321
                121
                종료
                """.stripIndent());

        //new QuoteApp(sc).run();

        sc.close();
    }

    @Test
    @DisplayName("삭제하기")
    void t3() {
        Scanner sc = TestUtil.genScanner("""
                삭제?id=4
                종료
                """.stripIndent());

        //new QuoteApp(sc).run();

        sc.close();
    }

}
