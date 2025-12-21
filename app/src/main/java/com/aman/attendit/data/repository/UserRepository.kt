package com.aman.attendit.data.repository

import com.aman.attendit.data.local.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

}
