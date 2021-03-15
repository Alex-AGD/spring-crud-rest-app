package backend.restapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TOOLS")

public class Tools {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ToolName", length = 64, nullable = false)
    private String toolName;

    @Temporal(TemporalType.DATE)
    @Column(name = "Date")
    private Date dateOfTools;

    @Column(name = "Cost", nullable = false)
    private Long cost;

    public Tools() {

    }

    public Tools(String toolName, Date dateOfTools, Long cost) {
        this.toolName = toolName;
        this.dateOfTools = dateOfTools;
        this.cost = cost;
    }


    public Long getId() {
        return id;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public Date getDateOfTools() {
        return dateOfTools;
    }

    public void setDateOfTools(Date dateOfTools) {
        this.dateOfTools = dateOfTools;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}
