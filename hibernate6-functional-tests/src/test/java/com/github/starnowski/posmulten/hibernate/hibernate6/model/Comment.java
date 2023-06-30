package com.github.starnowski.posmulten.hibernate.hibernate6.model;


import com.github.starnowski.posmulten.hibernate.common.TenantTable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Entity example with custom tenant column name "comment_tenant_id".
 * Column is not created by hibernate hibernate5 but by posmulten-hibernate library.
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "comments")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TenantTable(tenantIdColumn = "comment_tenant_id")
public class Comment {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User author;
    @ManyToOne
    private Post post;
    @Column(columnDefinition = "text")
    private String text;
}
