package com.vovka.egov66client.data.di

import com.vovka.egov66client.data.repo.ScheduleRepositoryImpl
import com.vovka.egov66client.data.repo.StudentRepositoryImpl
import com.vovka.egov66client.domain.schedule.repo.ScheduleRepository
import com.vovka.egov66client.domain.student.repo.StudentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

//    @Binds
//    abstract fun bindAccountrepo(
//        impl: AuthorizationRepositoryImpl
//    ): AuthorizationRepository

    @Binds
    abstract fun bindStudentRepo(
        impl: StudentRepositoryImpl
    ): StudentRepository

    @Binds
    abstract fun bindScheduleRepo(
        impl: ScheduleRepositoryImpl
    ): ScheduleRepository
}