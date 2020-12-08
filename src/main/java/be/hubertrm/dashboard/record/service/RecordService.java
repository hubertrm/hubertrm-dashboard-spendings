package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.model.Record;
import be.hubertrm.dashboard.record.repository.SpendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class RecordService implements IQueryService {

    @Autowired
    SpendingRepository spendingRepository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public List<Record> getAllRecords() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TypedQuery<Record> typedQuery = entityManager.createQuery(
                "SELECT NEW be.hubertrm.dashboard.record.model.Record(sp.id, sp.spendingDate, sp.amount, ca.name, ac.name, sp.note)"
                        +"FROM Spending sp, Category ca,"
                        +" Account ac WHERE sp.categoryId = ca.id AND sp.accountId = ac.id", Record.class);
        List<Record> recordList = typedQuery.getResultList();
        entityManager.close();

        return recordList;
    }
}
