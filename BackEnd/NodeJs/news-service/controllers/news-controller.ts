import { Request, Response } from 'express';
import path from 'path';
import fs from 'fs';


const lastUploadedFilePath = path.join(__dirname, 'lastUploadedFile.txt');

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

export async function retrieveUploadedFile(req: Request, res: Response): Promise<void> {
    try {
        const filename: string | null = getLastUploadedFile();
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


function getLastUploadedFile(): string | null {
    try {
        const data = fs.readFileSync(lastUploadedFilePath, 'utf-8');
        return data.trim();
    } catch (error) {
        return null;
    }
}

function updateLastUploadedFile(filename: string): void {
    fs.writeFileSync(lastUploadedFilePath, filename);
}
