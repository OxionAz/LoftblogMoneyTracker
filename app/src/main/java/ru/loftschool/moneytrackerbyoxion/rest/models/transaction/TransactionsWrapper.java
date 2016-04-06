package ru.loftschool.moneytrackerbyoxion.rest.models.transaction;

public class TransactionsWrapper {

    private Long id;
    private Long categoryId;
    private String comment;
    private String sum;
    private String date;

    public void setId(Long id) {
        this.id = id+1;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setSum(String sum) {
        this.sum = sum;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "{\"id\":"+id+"," +
                "\"category_id\":"+categoryId+"," +
                "\"comment\":\""+comment+"\"," +
                "\"sum\":\""+sum+"\"," +
                "\"tr_date\":\""+date+"\"}";
    }
}
