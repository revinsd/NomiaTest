import atm.ATM;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ATMTest {
    private ATM atm;

    @BeforeEach
    void initAtm() {
        atm = new ATM();
    }

    @Test
    void depositTest_correctArray() {
        var deposit = new int[]{1, 2, 3, 4, 5};

        atm.deposit(deposit);

        assertAtmBanknotesCount(deposit);
    }

    @Test
    void withdraw_sameBanknotes() {
        atm.deposit(new int[]{5, 0, 0, 0, 0});

        var receivedBanknotes = atm.withdraw(50);

        assertArrayEquals(new int[]{5, 0, 0, 0, 0}, receivedBanknotes);
        assertAtmBanknotesCount(new int[5]);
    }

    @Test
    void withdraw_differentBanknotes() {
        atm.deposit(new int[]{0, 0, 1, 0, 1});

        var receivedBanknotes = atm.withdraw(600);

        assertArrayEquals(new int[]{0, 0, 1, 0, 1}, receivedBanknotes);
        assertAtmBanknotesCount(new int[5]);
    }

    @Test
    void withdraw_someBanknotesLeft() {
        atm.deposit(new int[]{5, 1, 0, 0, 0});

        var receivedBanknotes = atm.withdraw(50);

        assertArrayEquals(new int[]{0, 1, 0, 0, 0}, receivedBanknotes);
        assertAtmBanknotesCount(new int[]{5, 0, 0, 0, 0});
    }

    @Test
    void withdraw_greaterBanknotePreventsWithdraw() {
        atm.deposit(new int[]{5, 0, 0, 0, 1});

        var receivedBanknotes = atm.withdraw(50);

        assertArrayEquals(new int[]{-1}, receivedBanknotes);
        assertAtmBanknotesCount(new int[]{5, 0, 0, 0, 1});
    }

    @Test
    void withdraw_notEnoughBanknotes() {
        var deposit = new int[]{1, 0, 0, 0, 0};
        atm.deposit(deposit);

        var receivedBanknotes = atm.withdraw(50);

        assertArrayEquals(new int[]{-1}, receivedBanknotes);
        assertAtmBanknotesCount(deposit);
    }

    @SneakyThrows
    private void assertAtmBanknotesCount(int[] expected) {
        var banknotesField = ATM.class.getDeclaredField("banknotesCount");
        banknotesField.setAccessible(true);
        var actual = (int[]) banknotesField.get(atm);
        assertArrayEquals(expected, actual);
    }

}
