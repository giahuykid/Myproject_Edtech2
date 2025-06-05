import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Box,
  Card,
  CardContent,
  CardActions,
  Button,
  IconButton,
  Chip
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import { 
  FlashcardCollection,
  getAllFlashcardCollections,
  deleteFlashcardCollection
} from '../services/flashcardService';

const FlashcardCollectionList: React.FC = () => {
  const [collections, setCollections] = useState<FlashcardCollection[]>([]);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const fetchCollections = async () => {
    try {
      const data = await getAllFlashcardCollections();
      setCollections(data);
    } catch (err) {
      setError('Failed to load flashcard collections');
      console.error('Error loading collections:', err);
    }
  };

  useEffect(() => {
    fetchCollections();
  }, []);

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this collection?')) {
      try {
        await deleteFlashcardCollection(id);
        fetchCollections(); // Refresh the list
      } catch (err) {
        setError('Failed to delete collection');
        console.error('Error deleting collection:', err);
      }
    }
  };

  const handleViewCollection = (id: number) => {
    navigate(`/collection/${id}`);
  };

  if (error) {
    return (
      <Container>
        <Typography color="error" sx={{ mt: 4 }}>
          {error}
        </Typography>
      </Container>
    );
  }

  return (
    <Container>
      <Box sx={{ mt: 4, mb: 4 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
          <Typography variant="h4" component="h1">
            My Flashcard Collections
          </Typography>
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate('/create')}
          >
            Create New Collection
          </Button>
        </Box>

        {collections.length === 0 ? (
          <Typography variant="body1" sx={{ textAlign: 'center', mt: 4 }}>
            No flashcard collections yet. Create your first collection!
          </Typography>
        ) : (
          <Box sx={{ 
            display: 'grid',
            gap: 3,
            gridTemplateColumns: {
              xs: '1fr',
              sm: 'repeat(2, 1fr)',
              md: 'repeat(3, 1fr)'
            }
          }}>
            {collections.map((collection) => (
              <Card 
                key={collection.id}
                sx={{ 
                  height: '100%',
                  display: 'flex',
                  flexDirection: 'column',
                  '&:hover': {
                    boxShadow: 6
                  }
                }}
              >
                <CardContent sx={{ flexGrow: 1 }}>
                  <Typography variant="h6" gutterBottom>
                    {collection.name}
                  </Typography>
                  <Chip 
                    label={`${collection.numberOfFlashcards} cards`}
                    color="primary"
                    size="small"
                    sx={{ mt: 1 }}
                  />
                </CardContent>
                <CardActions sx={{ justifyContent: 'space-between', p: 2 }}>
                  <Button
                    size="small"
                    color="primary"
                    onClick={() => handleViewCollection(collection.id)}
                  >
                    View Cards
                  </Button>
                  <IconButton
                    size="small"
                    color="error"
                    onClick={() => handleDelete(collection.id)}
                    aria-label="delete collection"
                  >
                    <DeleteIcon />
                  </IconButton>
                </CardActions>
              </Card>
            ))}
          </Box>
        )}
      </Box>
    </Container>
  );
};

export default FlashcardCollectionList; 