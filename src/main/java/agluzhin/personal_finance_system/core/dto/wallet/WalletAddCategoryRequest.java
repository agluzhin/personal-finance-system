package agluzhin.personal_finance_system.core.dto.wallet;

/**
 * DTO для запроса на добавление категории и ее значения в кошелек.
 * @param userId уникальное значение, присвоенное пользователю при создании;
 * @param categoryType тип категории: ДОХОДЫ/РАСХОДЫ;
 * @param categoryName желаемое название категории;
 * @param categoryValue значение по данной категории.
 */
public record WalletAddCategoryRequest(
        String userId,
        String categoryType,
        String categoryName,
        String categoryValue
) {
}
