import { Request, Response } from 'express';
import path from 'path';

export async function handleFileUpload(req: Request, res: Response): Promise<void> {
    try {
        if (!req.file) {
            res.status(400).json({ error: 'No file uploaded' });
        } else {
            const originalFilename = req.file.originalname;
            res.json({ filename: originalFilename });
        }
    } catch (error) {
        res.status(500).json({ error: 'Something went wrong' });
    }
}

export async function retrieveUploadedFile(req: Request, res: Response): Promise<void> {
    try {
        const filename: string = req.params.filename;
        const filePath: string = path.join(__dirname, '../uploads', filename);
        res.sendFile(filePath);
    } catch (error) {
        res.status(500).json({ error: 'Something went wrong' });
    }
}







