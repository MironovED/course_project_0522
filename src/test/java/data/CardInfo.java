package data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static data.DataGenerator.*;

@Data
@RequiredArgsConstructor
@Value
public class CardInfo {
        private String name;
        private String CardNumber;
        private String codeCvcCvv;
        private String month;
        private String year;

        public static CardInfo getApprovedCard () {
            return new CardInfo(
                    getValidName("EN"),
                    "1111_2222_3333_4444",
                    DataGenerator.getCodeCvcCvv(),
                    getThisMonth(),
                    getThisYear());
        }

        public static CardInfo getDeclinedCard () {
            return new CardInfo(
                    getValidName("EN"),
                    "5555_6666_7777_8888",
                    DataGenerator.getCodeCvcCvv(),
                    getThisMonth(),
                    getThisYear());
        }


}
