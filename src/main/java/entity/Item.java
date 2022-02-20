package entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn // 서브 테이블 매칭을 위한 DTYPE 자동 생성 어노테이션.
                    // @DiscriminatorColumn(name = "DIS_TYPE") <-- 이름을 지정할 수 있다(참고: DTYPE 이 관례)
                    // !!! 싱글테이블 전략에서는 이 어노테이션을 필수로 추가해야 한다.(JOIN 테이블의 경우 생략해도 찾아 갈 수는 있다)
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
