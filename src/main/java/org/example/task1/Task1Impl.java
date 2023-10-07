package org.example.task1;

import org.example.task1.comparator.StringArrayComparator;

import java.util.List;

/**
 * <h1>Задание №1</h1>
 * Реализуйте интерфейс {@link IStringRowsListSorter}.
 *
 * <p>Мы будем обращать внимание в первую очередь на структуру кода и владение стандартными средствами java.</p>
 */
public class Task1Impl implements IStringRowsListSorter {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    public static final IStringRowsListSorter INSTANCE = getInstance();

    private static Task1Impl task1Instance;

    private Task1Impl() {
    }

    public static IStringRowsListSorter getInstance() {
        Task1Impl localInstance = task1Instance;
        if(localInstance == null) {
            synchronized (Task1Impl.class) {
                localInstance = task1Instance;
                if(localInstance == null){
                    task1Instance = localInstance = new Task1Impl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void sort(List<String[]> rows, final int columnIndex) {
        rows.sort(new StringArrayComparator(columnIndex));
    }
}
