package com.mickey305.util.file.zip;

import com.mickey305.util.file.exception.FilePathException;
import com.mickey305.util.Log;

import java.util.Collection;
import java.util.Map;

import static com.mickey305.util.file.exception.model.ExceptionValues.CD_EXISTENCE_FILE_PATH;

/**
 * Created by K.Misaki on 2017/04/09.
 *
 */
public class ZipComponent {
    private static final ZipCompressor compressor = ZipCompressor.getInstance();
    private static final ZipDeCompressor deCompressor = ZipDeCompressor.getInstance();

    public interface CompressOKCallback {
        void onSuccess(String inPath, String outPath);
    }

    public interface CompressBadCallback {
        void onError(Throwable th);
    }

    private ZipComponent() {
        Log.d(ZipComponent.class, "ZipComponent", ": created zip compressor");
        Log.d(ZipComponent.class, "ZipComponent", ": created zip deCompressor");
    }

    /**
     * 圧縮する
     * @param inDirPath 入力フォルダパス名
     * @param outFilePath 出力ファイルパス名
     * @param compressOKCallback コールバックインタフェース（処理成功用）
     * @param compressBadCallback コールバックインタフェース（処理失敗用）
     */
    public synchronized static void compressDir(String inDirPath, String outFilePath, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
        try {
            compressor.compressDir(inDirPath, outFilePath);
        } catch (FilePathException e) {
            if (e.getResultCode() != CD_EXISTENCE_FILE_PATH) {
                if (compressBadCallback != null)
                    compressBadCallback.onError(e);
                return;
            }
        }
        if (compressOKCallback != null)
            compressOKCallback.onSuccess(inDirPath, outFilePath);
    }

    /**
     * 圧縮する
     * @param inFilePath 入力ファイルパス名
     * @param outFilePath 出力ファイルパス名
     * @param compressOKCallback コールバックインタフェース（処理成功用）
     * @param compressBadCallback コールバックインタフェース（処理失敗用）
     */
    public synchronized static void compress(String inFilePath, String outFilePath, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
        try {
            compressor.compress(inFilePath, outFilePath);
        } catch (FilePathException e) {
            if (e.getResultCode() != CD_EXISTENCE_FILE_PATH) {
                if (compressBadCallback != null)
                    compressBadCallback.onError(e);
                return;
            }
        }
        if (compressOKCallback != null)
            compressOKCallback.onSuccess(inFilePath, outFilePath);
    }

    /**
     * 圧縮する
     * @param inFilePaths 入力ファイルパス名
     * @param outFilePath 出力ファイルパス名
     * @param compressOKCallback コールバックインタフェース（処理成功用）
     * @param compressBadCallback コールバックインタフェース（処理失敗用）
     */
    public synchronized static void compressLst(Collection<String> inFilePaths, String outFilePath, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
        try {
            compressor.compress(inFilePaths, outFilePath);
        } catch (FilePathException e) {
            if (e.getResultCode() != CD_EXISTENCE_FILE_PATH) {
                if (compressBadCallback != null)
                    compressBadCallback.onError(e);
                return;
            }
        }
        if (compressOKCallback != null)
            compressOKCallback.onSuccess(inFilePaths.toString(), outFilePath);
    }

    /**
     * 解凍する
     * @param inFilePath 入力ファイルパス名
     * @param outDirPath 出力ファイルパス名
     * @param compressOKCallback コールバックインタフェース（処理成功用）
     * @param compressBadCallback コールバックインタフェース（処理失敗用）
     */
    public synchronized static void decompress(String inFilePath, String outDirPath, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
        try {
            deCompressor.decompress(inFilePath, outDirPath);
        } catch (FilePathException e) {
            // nop
        }
        if (compressOKCallback != null)
            compressOKCallback.onSuccess(inFilePath, outDirPath);
    }

    /**
     * 解凍する
     * @param inFilePaths 入力ファイルパス名
     * @param outFilePath 出力フォルダパス名
     * @param compressOKCallback コールバックインタフェース（処理成功用）
     * @param compressBadCallback コールバックインタフェース（処理失敗用）
     */
    public synchronized static void decompressLst(Collection<String> inFilePaths, String outFilePath, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
        try {
            deCompressor.decompress(inFilePaths, outFilePath);
        } catch (FilePathException e) {
            if (e.getResultCode() != CD_EXISTENCE_FILE_PATH) {
                if (compressBadCallback != null)
                    compressBadCallback.onError(e);
                return;
            }
        }
        if (compressOKCallback != null)
            compressOKCallback.onSuccess(inFilePaths.toString(), outFilePath);
    }

    // batch namespace
    public static class batch {
        /**
         * 圧縮する
         * @param paths 入力出力パスのマッピングオブジェクト
         * @param compressOKCallback コールバックインタフェース（処理成功用）
         * @param compressBadCallback コールバックインタフェース（処理失敗用）
         */
        public static void compressDir(Map<String, String> paths, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
            paths.forEach((inDirPath, outFilePath) -> ZipComponent.compressDir(inDirPath, outFilePath, compressOKCallback, compressBadCallback));
        }

        /**
         * 圧縮する
         * @param paths 入力出力パスのマッピングオブジェクト
         * @param compressOKCallback コールバックインタフェース（処理成功用）
         * @param compressBadCallback コールバックインタフェース（処理失敗用）
         */
        public static void compress(Map<String, String> paths, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
            paths.forEach((inFilePath, outFilePath) -> ZipComponent.compress(inFilePath, outFilePath, compressOKCallback, compressBadCallback));
        }

        /**
         * 圧縮する
         * @param paths 入力出力パスのマッピングオブジェクト
         * @param compressOKCallback コールバックインタフェース（処理成功用）
         * @param compressBadCallback コールバックインタフェース（処理失敗用）
         */
        public static void compressLst(Map<Collection<String>, String> paths, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
            paths.forEach((inFilePaths, outFilePath) -> ZipComponent.compressLst(inFilePaths, outFilePath, compressOKCallback, compressBadCallback));
        }

        /**
         * 解凍する
         * @param paths 入力出力パスのマッピングオブジェクト
         * @param compressOKCallback コールバックインタフェース（処理成功用）
         * @param compressBadCallback コールバックインタフェース（処理失敗用）
         */
        public static void decompress(Map<String, String> paths, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
            paths.forEach((inFilePath, outDirPath) -> ZipComponent.decompress(inFilePath, outDirPath, compressOKCallback, compressBadCallback));
        }

        /**
         * 解凍する
         * @param paths 入力出力パスのマッピングオブジェクト
         * @param compressOKCallback コールバックインタフェース（処理成功用）
         * @param compressBadCallback コールバックインタフェース（処理失敗用）
         */
        public static void decompressLst(Map<Collection<String>, String> paths, CompressOKCallback compressOKCallback, CompressBadCallback compressBadCallback) {
            paths.forEach((inFilePaths, outFilePath) -> ZipComponent.decompressLst(inFilePaths, outFilePath, compressOKCallback, compressBadCallback));
        }
    }
}
