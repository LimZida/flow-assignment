package madras.flow.assignment.common.enums;

/**
 * title : CodeEnum
 *
 * description : 일반 ENUM코드
 *
 * reference : ENUM 활용 예제 https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EC%97%B4%EA%B1%B0%ED%98%95Enum-%ED%83%80%EC%9E%85-%EB%AC%B8%EB%B2%95-%ED%99%9C%EC%9A%A9-%EC%A0%95%EB%A6%AC#final_%EC%83%81%EC%88%98
 *
 *
 * author : 임현영
 *
 * date : 2024.05.30
 **/
public enum CodeEnum {
    FIX("F"),
    SELECT("S"),
    INSERT("I"),
    DELETE("D"),
    UPDATE("U"),
    YES("Y"),
    NO("N"),
    CUSTOM("C"),
    LOCAL("local"),
    PRODUCTION("prd"),
    HTTP("http"),
    HTTPS("https"),
    EMPTY("");

    // 문자열을 저장할 필드
    private final String value;

	// 생성자
    private CodeEnum(String value) {
        this.value = value;
    }

	// Getter
    public String getValue() {
        return value;
    }
}
