package com.github.starnowski.posmulten.hibernate.core.model;


import com.github.starnowski.posmulten.hibernate.common.TenantTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Entity example with custom tenant column name "comment_tenant_id".
 * Column is not created by hibernate core but by posmulten-hibernate library.
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
