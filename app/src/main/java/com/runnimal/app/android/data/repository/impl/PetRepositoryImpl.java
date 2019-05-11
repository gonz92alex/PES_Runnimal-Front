package com.runnimal.app.android.data.repository.impl;

import com.runnimal.app.android.data.api.RunnimalApi;
import com.runnimal.app.android.data.repository.PetRepository;
import com.runnimal.app.android.domain.Pet;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class PetRepositoryImpl implements PetRepository {

    private final RunnimalApi api;

    @Inject
    public PetRepositoryImpl(RunnimalApi api) {
        this.api = api;
    }

    @Override
    public Observable<List<Pet>> list(String ownerEmail) {
        return Observable.create(emitter -> {
            api.listPets(new RunnimalApi.RunnimalApiCallback<List<Pet>>() {
                @Override
                public void responseOK(List<Pet> pets) {
                    emitter.onNext(pets);
                    emitter.onComplete();
                }

                @Override
                public void responseError(Exception e) {
                    emitter.onError(e);
                }
            });
        });
    }

    @Override
    public Observable<Pet> get(String id) {
        return Observable.create(emitter -> {
            api.getPet(id, //
                    new RunnimalApi.RunnimalApiCallback<Pet>() {
                        @Override
                        public void responseOK(Pet pet) {
                            emitter.onNext(pet);
                            emitter.onComplete();
                        }

                        @Override
                        public void responseError(Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Observable<String> modify(Pet pet) {
        return Observable.create(emitter -> {
            api.modifyPet(pet, //
                    new RunnimalApi.RunnimalApiCallback<String>() {
                        @Override
                        public void responseOK(String message) {
                            emitter.onNext(message);
                            emitter.onComplete();
                        }

                        @Override
                        public void responseError(Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Observable<Pet> create(Pet pet) {
        return Observable.create(emitter -> {
            api.createPet(pet, //
                    new RunnimalApi.RunnimalApiCallback<Pet>() {
                        @Override
                        public void responseOK(Pet pet) {
                            emitter.onNext(pet);
                            emitter.onComplete();
                        }

                        @Override
                        public void responseError(Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }
}
