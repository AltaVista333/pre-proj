package jm.task.core.jdbc.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "name", length = 15, nullable = false)
  private String name;

  @Column(name = "last_name", length = 15, nullable = false)
  private String lastName;

  @Column(name = "age", nullable = false)
  private Byte age;
}
