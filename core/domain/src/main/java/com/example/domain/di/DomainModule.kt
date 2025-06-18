package com.example.domain.di

import com.example.domain.repository.GeoRepository
import com.example.domain.repository.PhotoRepository
import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.DeleteUserProfileUseCaseImpl
import com.example.domain.usecase.FollowToUserByIdUseCaseImpl
import com.example.domain.usecase.GetActualGeoDataUseCaseImpl
import com.example.domain.usecase.GetAllFollowersUseCaseImpl
import com.example.domain.usecase.GetAllFollowingUseCaseImpl
import com.example.domain.usecase.GetAllFriendsUseCaseImpl
import com.example.domain.usecase.GetPersonProfileInfoByIdUseCaseImpl
import com.example.domain.usecase.GetPostDetailByIdUseCaseImpl
import com.example.domain.usecase.GetProfileInfoUseCaseImpl
import com.example.domain.usecase.GetSavedPostsUseCaseImpl
import com.example.domain.usecase.LoginUseCaseImpl
import com.example.domain.usecase.RegisterUseCaseImpl
import com.example.domain.usecase.UnFollowToUserByIdUseCaseImpl
import com.example.domain.usecase.UpdateProfileInfoUseCaseImpl
import com.example.domain.usecase.UploadAvatarPhotoUseCaseImpl
import com.example.domain.usecase.UploadPostPhotoUseCaseImpl
import com.example.domain.usecase.UploadWalkPhotoUseCaseImpl
import com.example.presentation.usecase.DeleteUserProfileUseCase
import com.example.presentation.usecase.FollowToUserByIdUseCase
import com.example.presentation.usecase.GetActualGeoDataUseCase
import com.example.presentation.usecase.GetAllFollowersUseCase
import com.example.presentation.usecase.GetAllFollowingUseCase
import com.example.presentation.usecase.GetAllFriendsUseCase
import com.example.presentation.usecase.GetPersonProfileInfoByIdUseCase
import com.example.presentation.usecase.GetPostDetailByIdUseCase
import com.example.presentation.usecase.GetProfileInfoUseCase
import com.example.presentation.usecase.GetSavedPostsUseCase
import com.example.presentation.usecase.LoginUseCase
import com.example.presentation.usecase.RegisterUseCase
import com.example.presentation.usecase.UnFollowToUserByIdUseCase
import com.example.presentation.usecase.UpdateProfileInfoUseCase
import com.example.presentation.usecase.UploadAvatarPhotoUseCase
import com.example.presentation.usecase.UploadPostPhotoUseCase
import com.example.presentation.usecase.UploadWalkPhotoUseCase
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
    fun provideUploadAvatarPhotoUseCase(
        photoRepository: PhotoRepository
    ): UploadAvatarPhotoUseCase {
        return UploadAvatarPhotoUseCaseImpl(photoRepository = photoRepository)
    }

    @Provides
    fun provideUploadPostPhotoUseCase(
        photoRepository: PhotoRepository
    ): UploadPostPhotoUseCase {
        return UploadPostPhotoUseCaseImpl(photoRepository = photoRepository)
    }

    @Provides
    fun provideUploadWalkPhotoUseCase(
        photoRepository: PhotoRepository
    ): UploadWalkPhotoUseCase {
        return UploadWalkPhotoUseCaseImpl(photoRepository = photoRepository)
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

    @Provides
    fun provideFollowToUserByIdUseCase(
        userRepository: UserRepository
    ): FollowToUserByIdUseCase{
        return FollowToUserByIdUseCaseImpl(userRepository = userRepository)
    }
    @Provides
    fun provideUnFollowToUserByIdUseCase(
        userRepository: UserRepository
    ): UnFollowToUserByIdUseCase {
        return UnFollowToUserByIdUseCaseImpl(userRepository = userRepository)
    }

    @Provides
    fun provideGetProfileInfoUseCase(
        userRepository: UserRepository
    ): GetProfileInfoUseCase {
        return GetProfileInfoUseCaseImpl(userRepository = userRepository)
    }


    @Provides
    fun provideGetPostDetailByIdUseCase(
        postRepository: PostRepository
    ): GetPostDetailByIdUseCase {
        return GetPostDetailByIdUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideGetSavedPostsUseCase(
        postRepository: PostRepository
    ): GetSavedPostsUseCase {
        return GetSavedPostsUseCaseImpl(postRepository = postRepository)
    }


}