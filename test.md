# Unit Tests - Wonderful Wander Project

## 📁 Структура модулей с тестами

### Модуль: `core/domain`
**Путь:** `core/domain/src/test/kotlin/com/wonderfulwander/domain/usecase/`

#### Протестированные Use Cases:

1. **CreateCommentUseCaseImplTest** - Тестирование создания комментариев
    - Проверка вызова репозитория с правильными параметрами
    - Тестирование успешного и неуспешного сценариев

2. **GetSavedPostsUseCaseImplTest** - Тестирование получения сохраненных постов
    - Пагинация (page, limit)
    - Возврат списка постов

3. **LoginUseCaseImplTest** - Тестирование авторизации
    - Проверка параметров логина
    - Обработка результата аутентификации

4. **UpdateProfileInfoUseCaseImplTest** - Тестирование обновления профиля
    - Получение информации профиля
    - Обработка ошибок

5. **CreatePostUseCaseImplTest** - Тестирование создания постов
    - Валидация параметров поста
    - Успешное создание

6. **CreateWalkUseCaseImplTest** - Тестирование создания прогулок
    - Параметры дистанции и времени
    - Интеграция с WalkRepository

7. **DeleteCommentUseCaseImplTest** - Тестирование удаления комментариев
    - Идентификация по postId и commentId
    - Успешное удаление

8. **DeletePostFromMyPostsUseCaseImplTest** - Тестирование удаления постов
    - Удаление из "моих постов"
    - Проверка идентификатора

9. **DeletePostFromMySavedPostsUseCaseImplTest** - Тестирование удаления из сохраненного
    - Удаление из закладок
    - Интеграция с репозиторием

10. **GetActualGeoDataUseCaseImplTest** - Тестирование геоданных
    - Геокодирование строки
    - Возврат координат и адреса

11. **GetAllCommentsByPostIdUseCaseImplTest** - Тестирование получения комментариев
    - Пагинация комментариев
    - Возврат списка по postId

12. **GetAllFollowingUseCaseImplTest** - Тестирование получения подписок
    - Пагинация списка подписок
    - Возврат списка пользователей

13. **GetMyPostsUseCaseImplTest** - Тестирование получения моих постов
    - Пагинация постов пользователя
    - Возврат собственных постов

14. **GetPersonProfileInfoByIdUseCaseImplTest** - Тестирование получения профиля по ID
    - Запрос по идентификатору
    - Возврат информации о пользователе

15. **GetPostsByUserIdUseCaseImplTest** - Тестирование получения постов пользователя
    - Пагинация постов другого пользователя
    - Фильтрация по userId

## 🚀 Запуск тестов

### Через командную строку:
```bash
# Все тесты модуля domain
./gradlew :core:domain:test

# Конкретный тестовый класс
./gradlew :core:domain:test --tests "*.CreateCommentUseCaseImplTest"

# Только успешные тесты
./gradlew :core:domain:test --continue

# С отчетом о покрытии
./gradlew :core:domain:testDebugUnitTestCoverage