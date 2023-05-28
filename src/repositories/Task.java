package repositories;

public class Task {
    private int id;

    private String name;
    private String description;

    public String getStatus() {
        return status;
    }

    private String status;

    public int getId() {
        return this.id;
    }

    public Task setId(int id) {
        this.id = id;
        return this;
    }
    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public Task setStatus(String status) {
        this.status = status;
        return this;
    }
    public Task setName(String name) {
        this.name = name;
        return this;
    }


    @Override
    public String toString() {
        return "id=[" + id+"]" +" name=[" + name +"] статус задачи=[" + status +"]";
    }

}



