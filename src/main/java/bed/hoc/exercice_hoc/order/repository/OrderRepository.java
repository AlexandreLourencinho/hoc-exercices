package bed.hoc.exercice_hoc.order.repository;

import bed.hoc.exercice_hoc.order.entity.OrderEntity;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    Optional<OrderEntity> findByUser(UserEntity user);

    void deleteByUser(UserEntity user);

}
