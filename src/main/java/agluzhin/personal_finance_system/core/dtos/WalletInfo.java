package agluzhin.personal_finance_system.core.dtos;

import java.math.BigDecimal;
import java.util.Map;

/**
 * DTO для вывода информации о "кошельке".
 * @param totalIncome значение общего дохода в "кошельке";
 * @param incomes коллекция значений категорий "кошелька" по типу "income";
 * @param totalExpenditure значение общего расхода в "кошельке";
 * @param expenditures коллекция значений категорий "кошелька" по типу "expenditure";
 * @param budgets коллекция значений бюджетов "кошелька".
 */
public record WalletInfo (
        BigDecimal totalIncome,
        Map<String, BigDecimal> incomes,
        BigDecimal totalExpenditure,
        Map<String, BigDecimal> expenditures,
        Map<String, BigDecimal> budgets
) {
}
