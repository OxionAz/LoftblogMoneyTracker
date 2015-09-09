package ru.loftschool.loftblogmoneytracker.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import java.util.List;
import java.util.concurrent.locks.Condition;
import ru.loftschool.loftblogmoneytracker.database.AppDatabase;

/**
 * Created by Александр on 08.09.2015.
 */

@ModelContainer
@Table(databaseName = AppDatabase.NAME)
public class Categories extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String name;

    public Categories(){}

    List<Expenses> expenses;

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "expenses")
    public List<Expenses> getExpenses(){
        if (expenses == null){
            expenses = new Select()
                    .from(Expenses.class)
                    .where(com.raizlabs.android.dbflow.sql.builder
                            .Condition.column(Expenses$Table.CATEGORIESFOREIGNKEYCONTAINER_CATEGORY_ID).is(id))
                    .queryList();
        }
        return expenses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
