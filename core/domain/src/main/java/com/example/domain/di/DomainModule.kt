package com.example.domain.di

import com.example.domain.repository.GeoRepository
import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.DeleteUserProfileUseCaseImpl
import com.example.domain.usecase.GetActualGeoDataUseCaseImpl
import com.example.domain.usecase.GetAllFollowersUseCaseImpl
import com.example.domain.usecase.GetAllFollowingUseCaseImpl
import com.example.domain.usecase.GetAllFriendsUseCaseImpl
import com.example.domain.usecase.GetPersonProfileInfoByIdUseCaseImpl
import com.example.domain.usecase.LoginUseCaseImpl
import com.example.domain.usecase.RegisterUseCaseImpl
import com.example.domain.usecase.UpdateProfileInfoUseCaseImpl
import com.example.domain.usecase.UploadPhotoUseCaseImpl
import com.example.presentation.usecase.DeleteUserProfileUseCase
import com.example.presentation.usecase.GetActualGeoDataUseCase
import com.example.presentation.usecase.GetAllFollowersUseCase
import com.example.presentation.usecase.GetAllFollowingUseCase
import com.example.presentation.usecase.GetAllFriendsUseCase
import com.example.presentation.usecase.GetPersonProfileInfoByIdUseCase
import com.example.presentation.usecase.LoginUseCase
import com.example.presentation.usecase.RegisterUseCase
import com.example.presentation.usecase.UpdateProfileInfoUseCase
import com.example.presentation.usecase.UploadPhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {


    @Provides
    fun provideGetActualGeoDataUseCase(
        geoRepository: GeoRepository
    ): GetActualGeoDataUseCase {
        return GetActualGeoDataUseCaseImpl(geoRepository = geoRepository)
    }

    @Provides
    fun provideRegisterUseCase(
        userRepository: UserRepository
    ): RegisterUseCase {
        return RegisterUseCaseImpl(userRepository = userRepository)
    }

    @Provides
    fun provideDeleteUserProfileUseCase(
        userRepository: UserRepository
    ): DeleteUserProfileUseCase {
        return DeleteUserProfileUseCaseImpl(userRepository = userRepository)
    }

    @Provides
    fun provideGetAllFriendsUseCase(
        userRepository: UserRepository
    ): GetAllFriendsUseCase {
        return GetAllFriendsUseCaseImpl(userRepository = userRepository)
    }

    @Provides
    fun provideLoginUseCase(
        userRepository: UserRepository
    ): LoginUseCase {
        return LoginUseCaseImpl(userRepository = userRepository)
    }

    @Provides
    fun provideUpdateProfileInfoUseCase(
        userRepository: UserRepository
    ): UpdateProfileInfoUseCase {
        return UpdateProfileInfoUseCaseImpl(userRepository = userRepository)
    }

    @Provides
    fun provideUpdateUploadPhotoUseCase(
        postRepository: PostRepository
    ): UploadPhotoUseCase {
        return UploadPhotoUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideGetAllFollowingUseCase(
        userRepository: UserRepository
    ): GetAllFollowingUseCase {
        return GetAllFollowingUseCaseImpl(userRepository = userRepository)
    }

    @Provides
    fun provideGetAllFollowersUseCase(
        userRepository: UserRepository
    ): GetAllFollowersUseCase {
        return GetAllFollowersUseCaseImpl(userRepository = userRepository)
    }

    @Provides
    fun provideGetPersonProfileInfoUseCase(
        userRepository: UserRepository
    ): GetPersonProfileInfoByIdUseCase {
        return GetPersonProfileInfoByIdUseCaseImpl(userRepository = userRepository)
    }

}