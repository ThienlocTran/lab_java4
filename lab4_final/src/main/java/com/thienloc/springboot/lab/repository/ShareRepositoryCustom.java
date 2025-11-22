package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.entity.Share;
import java.util.List;

public interface ShareRepositoryCustom {
    List<Share> findAllShares();
}
