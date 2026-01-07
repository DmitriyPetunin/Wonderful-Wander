package com.example.domain.di

import com.example.domain.repository.GeoRepository
import com.example.domain.repository.PhotoRepository
import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
import com.example.domain.repository.WalkRepository
import com.example.domain.usecaseimpl.CreateCommentUseCaseImpl
import com.example.domain.usecaseimpl.CreatePostUseCaseImpl
import com.example.domain.usecaseimpl.CreateWalkUseCaseImpl
import com.example.domain.usecaseimpl.DeleteCommentUseCaseImpl
import com.example.domain.usecaseimpl.DeletePostFromMyPostsUseCaseImpl
import com.example.domain.usecaseimpl.DeletePostFromMySavedPostsUseCaseImpl
import com.example.domain.usecaseimpl.DeleteUserProfileUseCaseImpl
import com.example.domain.usecaseimpl.FollowToUserByIdUseCaseImpl
import com.example.domain.usecaseimpl.GetActualGeoDataUseCaseImpl
import com.example.domain.usecaseimpl.GetAllCategoriesUseCaseImpl
import com.example.domain.usecaseimpl.GetAllCommentsByPostIdUseCaseImpl
import com.example.domain.usecaseimpl.GetAllFollowersUseCaseImpl
import com.example.domain.usecaseimpl.GetAllFollowingUseCaseImpl
import com.example.domain.usecaseimpl.GetAllFriendsUseCaseImpl
import com.example.domain.usecaseimpl.GetMyPostsUseCaseImpl
import com.example.domain.usecaseimpl.GetPersonProfileInfoByIdUseCaseImpl
import com.example.domain.usecaseimpl.GetPostDetailByIdUseCaseImpl
import com.example.domain.usecaseimpl.GetPostsByUserIdUseCaseImpl
import com.example.domain.usecaseimpl.GetProfileInfoUseCaseImpl
import com.example.domain.usecaseimpl.GetRecommendedPostsUseCaseImpl
import com.example.domain.usecaseimpl.GetSavedPostsByUserIdUseCaseImpl
import com.example.domain.usecaseimpl.GetSavedPostsUseCaseImpl
import com.example.domain.usecaseimpl.LikePostUseCaseImpl
import com.example.domain.usecaseimpl.LoginUseCaseImpl
import com.example.domain.usecaseimpl.RegisterUseCaseImpl
import com.example.domain.usecaseimpl.SavePostByPostIdUseCaseImpl
import com.example.domain.usecaseimpl.UnFollowToUserByIdUseCaseImpl
import com.example.domain.usecaseimpl.UpdateProfileInfoUseCaseImpl
import com.example.domain.usecaseimpl.UploadAvatarPhotoUseCaseImpl
import com.example.domain.usecaseimpl.UploadPostPhotoUseCaseImpl
import com.example.domain.usecaseimpl.UploadWalkPhotoUseCaseImpl
import com.example.domain.usecase.CreateCommentUseCase
import com.example.domain.usecase.CreatePostUseCase
import com.example.domain.usecase.CreateWalkUseCase
import com.example.domain.usecase.DeleteCommentUseCase
import com.example.domain.usecase.DeletePostFromMyPostsUseCase
import com.example.domain.usecase.DeletePostFromMySavedPostsUseCase
import com.example.domain.usecase.DeleteUserProfileUseCase
import com.example.domain.usecase.FollowToUserByIdUseCase
import com.example.domain.usecase.GetActualGeoDataUseCase
import com.example.domain.usecase.GetAllCategoriesUseCase
import com.example.domain.usecase.GetAllCommentsByPostIdUseCase
import com.example.domain.usecase.GetAllFollowersUseCase
import com.example.domain.usecase.GetAllFollowingUseCase
import com.example.domain.usecase.GetAllFriendsUseCase
import com.example.domain.usecase.GetMyPostsUseCase
import com.example.domain.usecase.GetPersonProfileInfoByIdUseCase
import com.example.domain.usecase.GetPostDetailByIdUseCase
import com.example.domain.usecase.GetPostsByUserIdUseCase
import com.example.domain.usecase.GetProfileInfoUseCase
import com.example.domain.usecase.GetRecommendedPostsUseCase
import com.example.domain.usecase.GetSavedPostsByUserIdUseCase
import com.example.domain.usecase.GetSavedPostsUseCase
import com.example.domain.usecase.LikePostUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.RegisterUseCase
import com.example.domain.usecase.SavePostByPostIdUseCase
import com.example.domain.usecase.UnFollowToUserByIdUseCase
import com.example.domain.usecase.UpdateProfileInfoUseCase
import com.example.domain.usecase.UploadAvatarPhotoUseCase
import com.example.domain.usecase.UploadPostPhotoUseCase
import com.example.domain.usecase.UploadWalkPhotoUseCase
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

    @Provides
    fun provideGetMyPostsUseCase(
        postRepository: PostRepository
    ): GetMyPostsUseCase{
        return GetMyPostsUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideDeletePostFromMySavedPostsUseCase(
        postRepository: PostRepository
    ): DeletePostFromMySavedPostsUseCase {
        return DeletePostFromMySavedPostsUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideDeletePostFromMyPostsUseCase(
        postRepository: PostRepository
    ): DeletePostFromMyPostsUseCase {
        return DeletePostFromMyPostsUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideGetPostsByUserIdUseCase(
        postRepository: PostRepository
    ): GetPostsByUserIdUseCase {
        return GetPostsByUserIdUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideGetSavedPostsByUserIdUseCase(
        postRepository: PostRepository
    ): GetSavedPostsByUserIdUseCase {
        return GetSavedPostsByUserIdUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideGetAllCategoriesUseCase(
        postRepository: PostRepository
    ): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCaseImpl(postRepository = postRepository)
    }


    @Provides
    fun provideSavePostByPostIdUseCase(
        postRepository: PostRepository
    ): SavePostByPostIdUseCase {
        return SavePostByPostIdUseCaseImpl(postRepository = postRepository)
    }


    @Provides
    fun provideCreatePostUseCase(
        postRepository: PostRepository
    ): CreatePostUseCase {
        return CreatePostUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideGetRecommendedPostsUseCase(
        postRepository: PostRepository
    ): GetRecommendedPostsUseCase {
        return GetRecommendedPostsUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideGetAllCommentsByPostIdUseCase(
        postRepository: PostRepository
    ): GetAllCommentsByPostIdUseCase {
        return GetAllCommentsByPostIdUseCaseImpl(postRepository = postRepository)
    }


    @Provides
    fun provideDeleteCommentUseCase(
        postRepository: PostRepository
    ): DeleteCommentUseCase {
        return DeleteCommentUseCaseImpl(postRepository = postRepository)
    }


    @Provides
    fun provideCreateCommentUseCase(
        postRepository: PostRepository
    ): CreateCommentUseCase {
        return CreateCommentUseCaseImpl(postRepository = postRepository)
    }


    @Provides
    fun provideLikePostUseCase(
        postRepository: PostRepository
    ): LikePostUseCase {
        return LikePostUseCaseImpl(postRepository = postRepository)
    }

    @Provides
    fun provideCreateWalkUseCase(
        walkRepository: WalkRepository
    ): CreateWalkUseCase {
        return CreateWalkUseCaseImpl(walkRepository = walkRepository)
    }
}