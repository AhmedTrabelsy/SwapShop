import { Request, Response } from 'express';
import path from 'path';

let lastUploadedFile: string = ''; // Variable to store the last uploaded file name

export async function handleFileUpload(req: Request, res: Response): Promise<void> {
    try {
        if (!req.file) {
            res.status(400).json({ error: 'No file uploaded' });
        } else {
            const originalFilename = req.file.originalname;
            lastUploadedFile = req.file.originalname; // Update the last uploaded file name
            res.json({ filename: originalFilename });
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

export async function retrieveUploadedFile(req: Request, res: Response): Promise<void> {
    try {
        const filename: string = lastUploadedFile; // Use the last uploaded file name
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








