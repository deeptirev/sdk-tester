package com.deepti.sdktest.repository;

import com.deepti.sdktest.model.SessionData;
import org.springframework.data.repository.CrudRepository;

public interface SessionDataRepository  extends CrudRepository<SessionData, String> {

}