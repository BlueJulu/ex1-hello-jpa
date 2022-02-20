package entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") // 회사 규약에 따라 다른 이름이 필요할 경우에 사용함. () 안에 문자열로 입력
public class Album extends Item{

    private String artist;

    public Album() {
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
