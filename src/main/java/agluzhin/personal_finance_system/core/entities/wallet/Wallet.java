package agluzhin.personal_finance_system.core.entities.wallet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс представляет собой сущность "Wallet" (кошелек). Имеет следующие параметры:
 * уникальный идентификатор (id), категории "income/expenditure" (categories), бюджеты по категориям (budgets).
 */
public class Wallet {
    String id;
    Map<String, Map<String, BigDecimal>> categories;
    Map<String, BigDecimal> budgets;


    /**
     * Конструктор создания экземпляра класса "Wallet".
     * @param id входное значение уникального идентификатора "кошелька".
     */
    public Wallet(String id) {
        // Присвоение случайного идентификатора.
        this.id = id;
        // Присвоение пустой коллекции категорий.
        categories = new HashMap<>();
        // Присвоение пустой коллекции бюджетов.
        budgets = new HashMap<>();
    }

    /**
     * Базовый getter для уникального идентификатора "кошелька".
     * @return значение по полю "id".
     */
    public String getId() {
        return id;
    }

    /**
     * Базовый getter для категорий "кошелька".
     * @return коллекция значений по полю "categories".
     */
    public Map<String, Map<String, BigDecimal>> getCategories() {
        return categories;
    }

    /**
     * Базовый getter для бюджетов "кошелька".
     * @return коллекция значений по полю "budgets".
     */
    public Map<String, BigDecimal> getBudgets() {
        return budgets;
    }
}
