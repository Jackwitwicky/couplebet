package com.jacknkiarie.couplebet.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jacknkiarie.couplebet.models.Bet;

import java.util.List;

public class BetRepository {

    private OldBetDao oldBetDao;
    private LiveData<List<Bet>> allBets;
    private LiveData<List<Bet>> completeBets;
    private LiveData<List<Bet>> ongoingBets;
    private LiveData<Bet> currentBet;

    public BetRepository(Application application) {
//        database.
//        BetR
        OldBetRoomDatabase db = OldBetRoomDatabase.getDatabase(application);
        oldBetDao = db.betDao();
        allBets = oldBetDao.getAllBets();
        completeBets = oldBetDao.getCompleteBets();
        ongoingBets = oldBetDao.getOngoingBets();
    }

    public LiveData<List<Bet>> getAllBets() {
        return allBets;
    }

    public LiveData<List<Bet>> getCompleteBets() {
        return completeBets;
    }

    public LiveData<List<Bet>> getOngoingBets() {
        return ongoingBets;
    }

    public void insert (Bet bet) {
        new insertAsyncTask(oldBetDao).execute(bet);
    }

    public LiveData<Bet> getBet(int uid) {
//        new queryAsyncTask(oldBetDao).execute(uid);
//        return oldBetDao.findBet(uid).get(0);
        // loop through all bets and return the one with id of
//        for (Bet bet : allBets.getValue()) {
//            if (bet.getUid() == uid) {
//                return bet;
//            }
//        }

        new queryAsyncTask(oldBetDao) {
            @Override
            protected void onPostExecute(LiveData<Bet> bet) {
                currentBet = bet;
            }
        }.execute(uid);

        return currentBet;
    }

    private static class insertAsyncTask extends AsyncTask<Bet, Void, Void> {
        private OldBetDao asyncTaskDao;

        insertAsyncTask(OldBetDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Bet... params) {
            asyncTaskDao.insert(params[0]);

            return null;
        }
    }


    private static class queryAsyncTask extends AsyncTask<Integer, Void, LiveData<Bet>> {
        private OldBetDao asyncTaskDao;

        queryAsyncTask(OldBetDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected LiveData<Bet> doInBackground(final Integer... params) {
            return asyncTaskDao.findBet(params[0]);

        }
    }
}
