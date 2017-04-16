package com.mickey305.util.file;

import com.mickey305.util.Log;
import com.mickey305.util.file.exception.model.ExceptionData;
import com.mickey305.util.file.exception.model.ExceptionResultSet;
import com.mickey305.util.file.exception.FilePathException;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.mickey305.util.file.exception.model.ExceptionValues.*;

/**
 * Created by K.Misaki on 2017/04/02.
 */
public class FileCheckMethods {
    /**
     *
     */
    public enum FileType{IN, OUT}

    /**
     *
     */
    public interface ErrorTaskCallback {
        void onErrorTask(Throwable th) throws FilePathException;
    }

    private FileCheckMethods() {}

    /**
     *
     * @param targetFile
     * @param type
     * @param taskCallback
     * @throws FilePathException
     */
    public static void chkDirHolder(@Nonnull final File targetFile, @Nonnull final FileType type, ErrorTaskCallback taskCallback) throws FilePathException {
        FilePathException exception;

        chkDirExistence(targetFile, type, taskCallback);

        if (type == FileType.IN) {
            if (targetFile.isDirectory()) {
                // 入力フォルダ
                Log.d(FileCheckMethods.class, "chkDirHolder", "[" + targetFile.getPath() + "] is input folder");
            } else {
                // 入力ファイル
                Log.d(FileCheckMethods.class, "chkDirHolder", "[" + targetFile.getPath() + "] is input file");
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                exception = new FilePathException(CD_IN_FILE_PATH, rs.get(CD_IN_FILE_PATH));
                if (taskCallback != null)
                    taskCallback.onErrorTask(exception);
                throw exception;
            }
        } else {
            if (targetFile.isDirectory()) {
                // 出力フォルダ
                Log.d(FileCheckMethods.class, "chkDirHolder", "[" + targetFile.getPath() + "] is output folder");
            } else {
                // 出力ファイル
                Log.d(FileCheckMethods.class, "chkDirHolder", "[" + targetFile.getPath() + "] is output file");
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                exception = new FilePathException(CD_OUT_FILE_PATH, rs.get(CD_OUT_FILE_PATH));
                if (taskCallback != null)
                    taskCallback.onErrorTask(exception);
                throw exception;
            }
        }
    }

    /**
     *
     * @param targetFile
     * @param type
     * @param taskCallback
     * @throws FilePathException
     */
    public static void chkFileHolder(@Nonnull final File targetFile, @Nonnull final FileType type, ErrorTaskCallback taskCallback) throws FilePathException {
        FilePathException exception;

        chkFileExistence(targetFile, type, taskCallback);

        if (type == FileType.IN) {
            if (targetFile.isFile()) {
                // 入力ファイル
                Log.d(FileCheckMethods.class, "chkFileHolder", "[" + targetFile.getPath() + "] is input file");
            } else {
                // 入力フォルダ
                Log.d(FileCheckMethods.class, "chkFileHolder", "[" + targetFile.getPath() + "] is input folder");
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                exception = new FilePathException(CD_IN_FILE_PATH, rs.get(CD_IN_FILE_PATH));
                if (taskCallback != null)
                    taskCallback.onErrorTask(exception);
                throw exception;
            }
        } else {
            if (targetFile.isFile()) {
                // 出力ファイル
                Log.d(FileCheckMethods.class, "chkFileHolder", "[" + targetFile.getPath() + "] is output file");
            } else {
                // 出力フォルダ
                Log.d(FileCheckMethods.class, "chkFileHolder", "[" + targetFile.getPath() + "] is output folder");
            }
        }
    }

    /**
     *
     * @param file
     * @param targetDir
     * @param taskCallback
     * @return
     * @throws FilePathException
     * @throws IOException
     */
    public static String validateFileName(@Nonnull final File file, @Nonnull final File targetDir, ErrorTaskCallback taskCallback) throws FilePathException, IOException {
        String filePath = file.getCanonicalPath();
        String targetPath = targetDir.getCanonicalPath();

        if (filePath.startsWith(targetPath)) {
            return filePath;
        } else {
            Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
            FilePathException exception = new FilePathException(CD_VALIDATION_FILE_PATH, rs.get(CD_VALIDATION_FILE_PATH));
            if (taskCallback != null)
                taskCallback.onErrorTask(exception);
            throw exception;
        }
    }

    /**
     *
     * @param targetFile
     * @param type
     * @param taskCallback
     * @throws FilePathException
     */
    private static void chkFileExistence(@Nonnull final File targetFile, @Nonnull final FileType type, ErrorTaskCallback taskCallback) throws FilePathException {
        if (type == FileType.IN) {
            if (!targetFile.exists()) {
                Log.d(FileCheckMethods.class, "chkFileExistence", "[" + targetFile.getPath() + "] is input file, not exist");
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                FilePathException exception = new FilePathException(CD_IN_FILE_PATH, rs.get(CD_IN_FILE_PATH));
                if (taskCallback != null)
                    taskCallback.onErrorTask(exception);
                throw exception;
            }
        } else {
            if (targetFile.exists()) {
                Log.d(FileCheckMethods.class, "chkFileExistence", "[" + targetFile.getPath() + "] is output file, exist");
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                FilePathException exception = new FilePathException(CD_EXISTENCE_FILE_PATH, rs.get(CD_EXISTENCE_FILE_PATH));
                if (taskCallback != null)
                    taskCallback.onErrorTask(exception);
                throw exception;
            } else {
                Log.d(FileCheckMethods.class, "chkFileExistence", "[" + targetFile.getPath() + "] is output file, not exist");
            }
        }
    }

    /**
     *
     * @param targetFile
     * @param type
     * @param taskCallback
     * @throws FilePathException
     */
    private static void chkDirExistence(@Nonnull final File targetFile, @Nonnull final FileType type, ErrorTaskCallback taskCallback) throws FilePathException {
        if (type == FileType.IN) {
            if (!targetFile.exists()) {
                Log.d(FileCheckMethods.class, "chkDirExistence", "[" + targetFile.getPath() + "] is input folder, not exist");
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                FilePathException exception = new FilePathException(CD_IN_FILE_PATH, rs.get(CD_IN_FILE_PATH));
                if (taskCallback != null)
                    taskCallback.onErrorTask(exception);
                throw exception;
            }
        } else {
            if (targetFile.exists()) {
                Log.d(FileCheckMethods.class, "chkDirExistence", "[" + targetFile.getPath() + "] is output folder, exist");
            } else {
                Log.d(FileCheckMethods.class, "chkDirExistence", "[" + targetFile.getPath() + "] is output folder, not exist");
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                FilePathException exception = new FilePathException(CD_NOT_EXISTENCE_DIR_PATH, rs.get(CD_NOT_EXISTENCE_DIR_PATH));
                if (taskCallback != null)
                    taskCallback.onErrorTask(exception);
                throw exception;
            }
        }
    }
}
