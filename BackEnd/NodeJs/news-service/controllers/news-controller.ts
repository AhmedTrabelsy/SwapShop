import { Request, Response } from 'express';
import path from 'path';

const news = require('../models/newsModel');

export async function handleFileUpload(req: Request, res: Response): Promise<void> {
    try {
        if (!req.file) {
            res.status(400).json({ error: 'No file uploaded' });
        } else {
            const originalFilename = req.file.originalname;
            res.json({ filename: originalFilename });
            updateLastUploadedFile(originalFilename);
        }
    } catch (error) {
        res.status(500).json({ error: 'Something went wrong' });
    }
}

// export async function retrieveUploadedFile(req: Request, res: Response): Promise<void> {
//     try {
//         const filename: string = req.params.filename;
//         const filePath: string = path.join(__dirname, '../uploads', filename);
//         res.sendFile(filePath);
//     } catch (error) {
//         res.status(500).json({ error: 'Something went wrong' });
//     }
// }

export async function retrieveUploadedFile(res: Response): Promise<void> {
    try {
        const filename: string | null = await getLastUploadedFile();
        if (!filename) {
            res.status(404).json({ error: 'No file found' });
            return;
        }

        const filePath: string = path.join(__dirname, '../uploads', filename);
        res.sendFile(filePath);
    } catch (error) {
        res.status(500).json({ error: 'Something went wrong' });
    }
}

export async function retrieveUploadedFileByName(req: Request, res: Response): Promise<void> {
    try {
        const filename: string = req.params.filename;
        const filePath: string = path.join(__dirname, '../uploads', filename);
        res.sendFile(filePath);
    } catch (error) {
        res.status(500).json({ error: 'Something went wrong' });
    }
}


async function getLastUploadedFile(): Promise<string | null> {
    try {
        const lastNews = await news.findOne().sort({ _id: -1 }).limit(1);
        if (lastNews) {
            return lastNews.path;
        } else {
            return null;
        }
    } catch (error) {
        throw error;
    }
}

async function updateLastUploadedFile(filename: string): Promise<void> {
    try {
        const newWishList = new news({
            path: filename,
        });
        await newWishList.save();
    } catch (error) {
        throw error;
    }
}
