package atm;

public class ATM {
    private final int[] banknotesCount;

    public ATM() {
        banknotesCount = new int[Banknote.values().length];
    }

    public synchronized void deposit(int[] banknotesCount) {
        for (int i = 0; i < banknotesCount.length; i++) {
            this.banknotesCount[i] += banknotesCount[i];
        }

    }

    public synchronized int[] withdraw(int amount) {
        var banknotesToWithdraw = new int[banknotesCount.length];
        for (Banknote banknote : Banknote.valuesSortedDesc()) {
            if (amount == 0) {
                break;
            }
            int index = banknote.getCode();
            int banknoteCount = banknotesCount[index];
            int banknotesSum = banknoteCount * banknote.getValue();
            if (amount < banknotesSum) {
                deposit(banknotesToWithdraw);
                return new int[]{-1};
            }
            amount -= banknotesSum;
            banknotesToWithdraw[index] = banknoteCount;
            banknotesCount[index] = 0;
        }
        if (amount > 0) {
            deposit(banknotesToWithdraw);
            return new int[]{-1};
        }
        return banknotesToWithdraw;
    }
}
