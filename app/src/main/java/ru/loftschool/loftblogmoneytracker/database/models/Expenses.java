package ru.loftschool.loftblogmoneytracker.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;
import ru.loftschool.loftblogmoneytracker.database.AppDatabase;

/**
 * Created by Александр on 08.09.2015.
 */

@Table(databaseName = AppDatabase.NAME)
public class Expenses extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String name;

    @Column
    private String price;

    public Expenses(){}

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "category_id",
            columnType = Integer.class,
            foreignColumnName = "id")},
            saveForeignKeyModel = false
    )
    ForeignKeyContainer<Categories> categoriesForeignKeyContainer;

    public void associateCategory(Categories categories){
        categoriesForeignKeyContainer = new ForeignKeyContainer<>(Categories.class);
        categoriesForeignKeyContainer.setModel(categories);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
